package com.mariamkatamashvili.gym.service.implementation;

import com.mariamkatamashvili.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvili.gym.dto.traineeDto.TraineeDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.ProfileResponseDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.RegistrationRequestDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.UpdateRequestDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.UpdateResponseDTO;
import com.mariamkatamashvili.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvili.gym.dto.trainingDto.TrainingsRequestDTO;
import com.mariamkatamashvili.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvili.gym.entity.Trainer;
import com.mariamkatamashvili.gym.entity.Training;
import com.mariamkatamashvili.gym.entity.TrainingType;
import com.mariamkatamashvili.gym.entity.User;
import com.mariamkatamashvili.gym.exception.GymException;
import com.mariamkatamashvili.gym.security.GymUserDetails;
import com.mariamkatamashvili.gym.service.TokenService;
import com.mariamkatamashvili.gym.service.TrainerService;
import com.mariamkatamashvili.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvili.gym.generator.PasswordGenerator;
import com.mariamkatamashvili.gym.generator.UsernameGenerator;
import com.mariamkatamashvili.gym.repository.TrainerRepository;
import com.mariamkatamashvili.gym.repository.TrainingRepository;
import com.mariamkatamashvili.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvili.gym.repository.UserRepository;
import com.mariamkatamashvili.gym.security.JwtTokenGenerator;
import com.mariamkatamashvili.gym.validator.Validator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepo;
    private final UserRepository userRepo;
    private final TrainingTypeRepository trainingTypeRepo;
    private final UsernameGenerator usernameGenerator;
    private final PasswordGenerator passwordGenerator;
    private final Validator validator;
    private final TrainingRepository trainingRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final TokenService tokenService;

    private static final String USER_NOT_FOUND = "User not found";

    @Override
    @Transactional
    public RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO) {
        try {
            String firstName = registrationRequestDTO.getFirstName();
            String lastName = registrationRequestDTO.getLastName();
            String username = usernameGenerator.generateUsername(firstName, lastName);
            String password = passwordGenerator.generatePassword();
            User user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .isActive(true)
                    .build();
            TrainingType type = trainingTypeRepo.findById(registrationRequestDTO.getSpecialization().getTrainingTypeId()).orElseThrow(() -> new EntityNotFoundException("TrainingType not found for id: " + registrationRequestDTO.getSpecialization().getTrainingTypeId()));
            Trainer trainer = Trainer.builder()
                    .specialization(type)
                    .user(user)
                    .build();
            trainerRepo.save(trainer);
            GymUserDetails userDetails = new GymUserDetails(user);
            return tokenService.register(userDetails, username, password);
        } catch (Exception e) {
            throw new GymException("Could not create trainer due to an unexpected error");
        }
    }

    @Override
    @PreAuthorize("#username == authentication.principal.username")
    public ProfileResponseDTO getProfile(String username) {
        validator.validateTrainerExists(username);

        Trainer trainer = trainerRepo.findByUsername(username);
        TrainingTypeDTO specialization = new TrainingTypeDTO(
                trainer.getSpecialization().getId(),
                trainer.getSpecialization().getTrainingTypeName()
        );

        List<TraineeDTO> trainees = trainer.getTrainees().stream().map(trainee -> new TraineeDTO(
                trainee.getUser().getUsername(),
                trainee.getUser().getFirstName(),
                trainee.getUser().getLastName()
        )).toList();

        return new ProfileResponseDTO(
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),
                specialization,
                trainer.getUser().getIsActive(),
                trainees
        );
    }

    @Override
    @Transactional
    @PreAuthorize("#updateRequestDTO.username == authentication.principal.username")
    public UpdateResponseDTO updateProfile(UpdateRequestDTO updateRequestDTO) {
        String username = updateRequestDTO.getUsername();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new GymException(USER_NOT_FOUND));
        validator.validateUserExists(username);
        Trainer trainer = trainerRepo.findByUsername(username);
        validator.validateTrainerExists(username);

        user.setFirstName(updateRequestDTO.getFirstName());
        user.setLastName(updateRequestDTO.getLastName());
        user.setIsActive(updateRequestDTO.getIsActive());
        userRepo.save(user);
        TrainingTypeDTO specialization = new TrainingTypeDTO(
                trainer.getSpecialization().getId(),
                trainer.getSpecialization().getTrainingTypeName()
        );
        List<TraineeDTO> trainees = trainer.getTrainees().stream().map(trainee -> new TraineeDTO(
                trainee.getUser().getUsername(),
                trainee.getUser().getFirstName(),
                trainee.getUser().getLastName()
        )).toList();

        return new UpdateResponseDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                specialization,
                user.getIsActive(),
                trainees
        );
    }

    @Override
    @PreAuthorize("#trainingsRequestDTO.username == authentication.principal.username")
    public List<TrainingResponseDTO> getTrainings(TrainingsRequestDTO trainingsRequestDTO) {
        String username = trainingsRequestDTO.getUsername();
        validator.validateTrainerExists(username);
        LocalDate startDate = trainingsRequestDTO.getStartDate();
        LocalDate endDate = trainingsRequestDTO.getEndDate();
        String traineeName = trainingsRequestDTO.getName();

        List<Training> trainings = trainingRepo.findTrainerTrainingsByCriteria(
                username,
                startDate,
                endDate,
                traineeName
        );
        return trainings.stream().map(t -> {
            TrainingTypeDTO specialization = trainingTypeRepo.findByTrainingTypeName(t.getTrainingType().getTrainingTypeName())
                    .map(tt -> new TrainingTypeDTO(tt.getId(), tt.getTrainingTypeName()))
                    .orElseThrow(() -> new GymException("Training type not found with name: " + t.getTrainingType().getTrainingTypeName()));
            return new TrainingResponseDTO(
                    t.getTrainingName(),
                    t.getTrainingDate(),
                    specialization,
                    t.getDuration(),
                    t.getTrainee().getUser().getUsername());
        }).toList();
    }

    @Override
    @Transactional
    @PreAuthorize("#toggleActivationDTO.username == authentication.principal.username")
    public void toggleActivation(ToggleActivationDTO toggleActivationDTO) {
        String username = toggleActivationDTO.getUsername();
        validator.validateUserExists(toggleActivationDTO.getUsername());

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new GymException(USER_NOT_FOUND));
        user.setIsActive(toggleActivationDTO.getIsActive());
        userRepo.save(user);
    }
}
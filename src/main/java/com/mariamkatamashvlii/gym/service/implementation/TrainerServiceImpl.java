package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.TraineeDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingsRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.exception.TrainingTypeNotFoundException;
import com.mariamkatamashvlii.gym.exception.UserNotCreatedException;
import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.security.JwtTokenGenerator;
import com.mariamkatamashvlii.gym.service.TrainerService;
import com.mariamkatamashvlii.gym.validator.Validator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
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
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        try {
            log.info("[{}] Registering new trainer", transactionId);
            String firstName = registrationRequestDTO.getFirstName();
            String lastName = registrationRequestDTO.getLastName();
            String password = passwordGenerator.generatePassword();
            User user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(usernameGenerator.generateUsername(firstName, lastName))
                    .password(passwordEncoder.encode(password))
                    .isActive(true)
                    .build();
            TrainingType type = trainingTypeRepo.findById(registrationRequestDTO.getSpecialization().getTrainingTypeId()).orElseThrow(() -> new EntityNotFoundException("TrainingType not found for id: " + registrationRequestDTO.getSpecialization().getTrainingTypeId()));
            Trainer trainer = Trainer.builder()
                    .specialization(type)
                    .user(user)
                    .build();
            trainerRepo.save(trainer);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), password)
            );
            String token = jwtTokenGenerator.generateJwtToken(authentication);
            log.info("[{}] Registered new trainer with username: {}", transactionId, user.getUsername());
            return new RegistrationResponseDTO(user.getUsername(), password, token);
        } catch (Exception e) {
            log.error("[{}] Unexpected error during trainer creation: {}", transactionId, e.getMessage());
            throw new UserNotCreatedException("Could not create trainer due to an unexpected error");
        }
    }

    @Override
    public ProfileResponseDTO getProfile(String username) {
        String transactionId = UUID.randomUUID().toString();
        validator.validateTrainerExists(username);
        log.info("[{}] Fetching profile for username: {}", transactionId, username);

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

        log.info("[{}] Successfully fetched profile for username: {}", transactionId, username);
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
    public UpdateResponseDTO updateProfile(UpdateRequestDTO updateRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        String username = updateRequestDTO.getUsername();

        User user = userRepo.findByUsername(username);
        validator.validateUserExists(username);
        Trainer trainer = trainerRepo.findByUsername(username);
        validator.validateTrainerExists(username);
        log.info("[{}] Attempting to update profile for username: {}", transactionId, username);

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

        log.info("[{}] Profile updated successfully for username: {}", transactionId, username);
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
    public List<TrainingResponseDTO> getTrainings(TrainingsRequestDTO trainingsRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        String username = trainingsRequestDTO.getUsername();
        validator.validateTrainerExists(username);
        LocalDate startDate = trainingsRequestDTO.getStartDate();
        LocalDate endDate = trainingsRequestDTO.getEndDate();
        String traineeName = trainingsRequestDTO.getName();
        log.info("[{}] Fetching trainings for trainer: {}", transactionId, username);

        List<Training> trainings = trainingRepo.findTrainerTrainingsByCriteria(
                username,
                startDate,
                endDate,
                traineeName
        );
        return trainings.stream().map(t -> {
            TrainingTypeDTO specialization = trainingTypeRepo.findByTrainingTypeName(t.getTrainingType().getTrainingTypeName())
                    .map(tt -> new TrainingTypeDTO(tt.getId(), tt.getTrainingTypeName()))
                    .orElseThrow(() -> new TrainingTypeNotFoundException("Training type not found with name: " + t.getTrainingType().getTrainingTypeName()));
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
    public void toggleActivation(ToggleActivationDTO toggleActivationDTO) {
        String transactionId = UUID.randomUUID().toString();
        String username = toggleActivationDTO.getUsername();
        validator.validateUserExists(toggleActivationDTO.getUsername());
        log.info("[{}] Attempting to toggle activation for user: {}", transactionId, username);

        User user = userRepo.findByUsername(username);
        user.setIsActive(toggleActivationDTO.getIsActive());
        userRepo.save(user);
        log.info("[{}] Successfully toggled activation for user: {}. Now active: {}", transactionId, username, user.getIsActive());
    }
}
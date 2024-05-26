package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.client.WorkloadServiceClient;
import com.mariamkatamashvlii.gym.dto.ActionType;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.WorkloadDTO;
import com.mariamkatamashvlii.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateTrainersRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerUsernameDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingsRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.exception.GymException;
import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.security.GymUserDetails;
import com.mariamkatamashvlii.gym.security.JwtTokenGenerator;
import com.mariamkatamashvlii.gym.service.TokenService;
import com.mariamkatamashvlii.gym.service.TraineeService;
import com.mariamkatamashvlii.gym.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepo;
    private final UserRepository userRepo;
    private final TrainerRepository trainerRepo;
    private final TrainingRepository trainingRepository;
    private final UsernameGenerator usernameGenerator;
    private final PasswordGenerator passwordGenerator;
    private final Validator validator;
    private final TrainingRepository trainingRepo;
    private final TrainingTypeRepository trainingTypeRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final WorkloadServiceClient workloadServiceClient;
    private final TokenService tokenService;

    private static final String USER_NOT_FOUND = "User not found";
    private static final String TRAINEE_NOT_FOUND = "Trainee not found";

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
            Trainee trainee = Trainee.builder()
                    .birthday(registrationRequestDTO.getBirthday())
                    .address(registrationRequestDTO.getAddress())
                    .user(user)
                    .build();
            traineeRepo.save(trainee);
            GymUserDetails userDetails = new GymUserDetails(user);
            return tokenService.register(userDetails, username, password);
        } catch (Exception e) {
            throw new GymException("Could not create trainee due to an error: " + e.getMessage());
        }
    }

    @Override
    @PreAuthorize("#username == authentication.principal.username")
    public ProfileResponseDTO getProfile(String username) {
        validator.validateUserExists(username);
        validator.validateTraineeExists(username);

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new GymException(USER_NOT_FOUND));
        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null) {
            throw new GymException(TRAINEE_NOT_FOUND);
        }

        List<TrainerDTO> trainers = trainee.getTrainers().stream().map(trainer -> {
            TrainerDTO dto = new TrainerDTO();
            dto.setUsername(trainer.getUser().getUsername());
            dto.setFirstName(trainer.getUser().getFirstName());
            dto.setLastName(trainer.getUser().getLastName());
            TrainingTypeDTO specializationDTO = new TrainingTypeDTO(
                    trainer.getSpecialization().getId(),
                    trainer.getSpecialization().getTrainingTypeName()
            );
            dto.setSpecialization(specializationDTO);
            return dto;
        }).toList();

        return new ProfileResponseDTO(
                user.getFirstName(),
                user.getLastName(),
                trainee.getBirthday(),
                trainee.getAddress(),
                user.getIsActive(),
                trainers
        );
    }

    @Override
    @Transactional
    @PreAuthorize("#updateRequestDTO.username == authentication.principal.username")
    public UpdateResponseDTO updateProfile(UpdateRequestDTO updateRequestDTO) {
        validator.validateUserExists(updateRequestDTO.getUsername());
        validator.validateTraineeExists(updateRequestDTO.getUsername());

        User user = userRepo.findByUsername(updateRequestDTO.getUsername())
                .orElseThrow(() -> new GymException(USER_NOT_FOUND));
        user.setFirstName(updateRequestDTO.getFirstName());
        user.setLastName(updateRequestDTO.getLastName());
        user.setIsActive(updateRequestDTO.getIsActive());
        userRepo.save(user);

        Trainee trainee = traineeRepo.findByUsername(updateRequestDTO.getUsername());
        if (trainee == null) {
            throw new GymException(TRAINEE_NOT_FOUND);
        }
        trainee.setBirthday(updateRequestDTO.getBirthday());
        trainee.setAddress(updateRequestDTO.getAddress());
        traineeRepo.save(trainee);

        List<TrainerDTO> trainers = trainee.getTrainers().stream().map(trainer -> {
            TrainerDTO dto = new TrainerDTO();
            dto.setUsername(trainer.getUser().getUsername());
            dto.setFirstName(trainer.getUser().getFirstName());
            dto.setLastName(trainer.getUser().getLastName());
            TrainingTypeDTO specializationDTO = new TrainingTypeDTO(
                    trainer.getSpecialization().getId(),
                    trainer.getSpecialization().getTrainingTypeName()
            );
            dto.setSpecialization(specializationDTO);
            return dto;
        }).toList();

        return new UpdateResponseDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                trainee.getBirthday(),
                trainee.getAddress(),
                user.getIsActive(),
                trainers
        );
    }

    @Override
    @Transactional
    @PreAuthorize("#username == authentication.principal.username")
    public void delete(String username) {
        validator.validateUserExists(username);
        validator.validateTraineeExists(username);

        removeTrainings(username);

        LocalDate now = LocalDate.now();
        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null) {
            throw new GymException(TRAINEE_NOT_FOUND);
        }

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new GymException(USER_NOT_FOUND));
        Set<Training> trainings = trainee.getTrainings();

        Set<Training> futureTrainings = trainings.stream()
                .filter(training -> training.getTrainingDate().isAfter(now))
                .collect(Collectors.toSet());
        for (Training training : futureTrainings) {
            trainingRepo.delete(training);
        }

        userRepo.delete(user);
    }

    @Override
    @PreAuthorize("#username == authentication.principal.username")
    public List<TrainerDTO> getUnassignedTrainers(String username) {
        validator.validateTraineeExists(username);

        List<TrainerDTO> unassignedTrainers = new ArrayList<>();
        Trainee trainee = traineeRepo.findByUsername(username);
        List<Trainer> traineeTrainers = trainee.getTrainers();
        List<Trainer> allTrainers = trainerRepo.findAll();

        for (Trainer t : allTrainers) {
            if (!traineeTrainers.contains(t)) {
                TrainerDTO dto = new TrainerDTO();
                dto.setUsername(t.getUser().getUsername());
                dto.setFirstName(t.getUser().getFirstName());
                dto.setLastName(t.getUser().getLastName());
                TrainingTypeDTO specializationDTO = new TrainingTypeDTO(
                        t.getSpecialization().getId(),
                        t.getSpecialization().getTrainingTypeName()
                );
                dto.setSpecialization(specializationDTO);
                unassignedTrainers.add(dto);
            }
        }

        return unassignedTrainers;
    }

    @Override
    @PreAuthorize("#trainingsRequestDTO.username == authentication.principal.username")
    public List<TrainingResponseDTO> getTrainings(TrainingsRequestDTO trainingsRequestDTO) {
        String username = trainingsRequestDTO.getUsername();
        validator.validateTraineeExists(username);
        String trainerName = trainingsRequestDTO.getName();
        LocalDate startDate = trainingsRequestDTO.getStartDate();
        LocalDate endDate = trainingsRequestDTO.getEndDate();
        TrainingTypeDTO trainingType = trainingsRequestDTO.getTrainingType();

        String trainingTypeName = trainingType != null ? trainingType.getTrainingTypeName() : null;
        List<Training> trainings = trainingRepo.findTraineeTrainingsByCriteria(
                username,
                startDate,
                endDate,
                trainerName,
                trainingTypeName
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
                    t.getTrainer().getUser().getUsername());
        }).toList();
    }

    @Override
    @Transactional
    @PreAuthorize("#updateTrainersRequestDTO.username == authentication.principal.username")
    public List<TrainerDTO> updateTrainers(UpdateTrainersRequestDTO updateTrainersRequestDTO) {
        String username = updateTrainersRequestDTO.getUsername();
        validator.validateTraineeExists(username);

        Trainee trainee = traineeRepo.findByUsername(username);
        List<TrainerDTO> newTrainers = new ArrayList<>();
        List<Trainer> updatedTrainers = updateTrainersRequestDTO.getTrainers().stream()
                .map(TrainerUsernameDTO::getUsername)
                .map(trainerRepo::findByUsername)
                .toList();
        trainee.setTrainers(updatedTrainers);
        traineeRepo.save(trainee);
        updatedTrainers.forEach(trainer -> {
            TrainerDTO dto = new TrainerDTO();
            dto.setUsername(trainer.getUser().getUsername());
            dto.setFirstName(trainer.getUser().getFirstName());
            dto.setLastName(trainer.getUser().getLastName());
            TrainingTypeDTO specializationDTO = new TrainingTypeDTO(
                    trainer.getSpecialization().getId(),
                    trainer.getSpecialization().getTrainingTypeName()
            );
            dto.setSpecialization(specializationDTO);
            newTrainers.add(dto);
        });

        return newTrainers;
    }

    @Override
    @Transactional
    @PreAuthorize("#toggleActivationDTO.username == authentication.principal.username")
    public void toggleActivation(ToggleActivationDTO toggleActivationDTO) {
        String username = toggleActivationDTO.getUsername();
        validator.validateUserExists(username);

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new GymException(USER_NOT_FOUND));
        user.setIsActive(toggleActivationDTO.getIsActive());
        userRepo.save(user);
    }

    private void removeTrainings(String username) {
        LocalDate now = LocalDate.now();
        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null) {
            throw new GymException(TRAINEE_NOT_FOUND);
        }

        Set<Training> trainings = trainee.getTrainings();

        Set<Training> futureTrainings = trainings.stream()
                .filter(training -> training.getTrainingDate().isAfter(now))
                .collect(Collectors.toSet());
        for (Training training : futureTrainings) {
            Trainer trainer = training.getTrainer();
            WorkloadDTO workload = WorkloadDTO.builder()
                    .username(trainer.getUser().getUsername())
                    .firstName(trainer.getUser().getFirstName())
                    .lastName(trainer.getUser().getLastName())
                    .isActive(trainer.getUser().getIsActive())
                    .date(training.getTrainingDate())
                    .duration(training.getDuration())
                    .actionType(ActionType.DELETE)
                    .build();
            workloadServiceClient.sendWorkload(workload);
        }
    }
}
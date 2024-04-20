package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
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
import com.mariamkatamashvlii.gym.exception.TrainingTypeNotFoundException;
import com.mariamkatamashvlii.gym.exception.UserNotCreatedException;
import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.security.JwtTokenGenerator;
import com.mariamkatamashvlii.gym.service.TraineeService;
import com.mariamkatamashvlii.gym.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
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
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        log.info("[{}] Attempting to register a new trainee", transactionId);
        try {
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
            Trainee trainee = Trainee.builder()
                    .birthday(registrationRequestDTO.getBirthday())
                    .address(registrationRequestDTO.getAddress())
                    .user(user)
                    .build();
            traineeRepo.save(trainee);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), password)
            );
            String token = jwtTokenGenerator.generateJwtToken(authentication);
            log.info("[{}] Successfully registered new trainee with username: {}", transactionId, user.getUsername());
            return new RegistrationResponseDTO(user.getUsername(), password, token);
        } catch (Exception e) {
            log.error("[{}] Failed to register trainee: {}", transactionId, e.getMessage(), e);
            throw new UserNotCreatedException("Could not create trainee due to an error: " + e.getMessage());
        }
    }

    @Override
    public ProfileResponseDTO getProfile(String username) {
        String transactionId = UUID.randomUUID().toString();
        validator.validateUserExists(username);
        validator.validateTraineeExists(username);
        log.info("[{}] Fetching profile for trainee: {}", transactionId, username);

        User user = userRepo.findByUsername(username);
        Trainee trainee = traineeRepo.findByUsername(username);

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

        log.info("[{}] Successfully fetched profile for trainee: {}", transactionId, username);
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
    public UpdateResponseDTO updateProfile(UpdateRequestDTO updateRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        validator.validateUserExists(updateRequestDTO.getUsername());
        validator.validateTraineeExists(updateRequestDTO.getUsername());
        log.info("[{}] Starting profile update for username: {}", transactionId, updateRequestDTO.getUsername());

        User user = userRepo.findByUsername(updateRequestDTO.getUsername());
        user.setFirstName(updateRequestDTO.getFirstName());
        user.setLastName(updateRequestDTO.getLastName());
        user.setIsActive(updateRequestDTO.getIsActive());
        userRepo.save(user);

        Trainee trainee = traineeRepo.findByUsername(updateRequestDTO.getUsername());
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

        log.info("[{}] Profile successfully updated for username: {}", transactionId, user.getUsername());
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
    public void delete(String username) {
        String transactionId = UUID.randomUUID().toString();
        validator.validateUserExists(username);
        validator.validateTraineeExists(username);
        log.info("[{}] Initiating deletion for username: {}", transactionId, username);

        Trainee trainee = traineeRepo.findByUsername(username);
        User user = userRepo.findByUsername(username);
        Set<Training> trainings = trainee.getTrainings();
        for (Training t : trainings) {
            trainingRepository.delete(t);
            log.debug("[{}] Deleted training with ID: {} for username: {}", transactionId, t.getId(), username);
        }

        userRepo.delete(user);
        log.info("[{}] Deleted user with username: {}", transactionId, username);
    }

    @Override
    public List<TrainerDTO> getUnassignedTrainers(String username) {
        String transactionId = UUID.randomUUID().toString();
        validator.validateTraineeExists(username);
        log.info("[{}] Fetching unassigned trainers for trainee: {}", transactionId, username);

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

        log.info("[{}] Successfully fetched unassigned trainers for trainee: {}. Number of trainers found: {}", transactionId, username, unassignedTrainers.size());
        return unassignedTrainers;
    }

    @Override
    public List<TrainingResponseDTO> getTrainings(TrainingsRequestDTO trainingsRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        String username = trainingsRequestDTO.getUsername();
        validator.validateTraineeExists(username);
        String trainerName = trainingsRequestDTO.getName();
        LocalDate startDate = trainingsRequestDTO.getStartDate();
        LocalDate endDate = trainingsRequestDTO.getEndDate();
        TrainingTypeDTO trainingType = trainingsRequestDTO.getTrainingType();
        log.info("[{}] Fetching trainings for trainee: {}", transactionId, username);

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
                    .orElseThrow(() -> new TrainingTypeNotFoundException("Training type not found with name: " + t.getTrainingType().getTrainingTypeName()));
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
    public List<TrainerDTO> updateTrainers(UpdateTrainersRequestDTO updateTrainersRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        String username = updateTrainersRequestDTO.getUsername();
        validator.validateTraineeExists(username);
        log.info("[{}] Starting to update trainers for trainee: {}", transactionId, username);

        Trainee trainee = traineeRepo.findByUsername(username);
        List<TrainerDTO> newTrainers = new ArrayList<>();
        List<Trainer> updatedTrainers = updateTrainersRequestDTO.getTrainers().stream()
                .map(TrainerUsernameDTO::getUsername)
                .map(trainerRepo::findByUsername)
                .toList();
        trainee.setTrainers(updatedTrainers);
        traineeRepo.save(trainee);
        log.info("Updated trainer list for trainee - {}", username);
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

        log.info("[{}] Successfully updated trainer list for trainee: {}", transactionId, username);
        return newTrainers;
    }

    @Override
    @Transactional
    public void toggleActivation(ToggleActivationDTO toggleActivationDTO) {
        String transactionId = UUID.randomUUID().toString();
        String username = toggleActivationDTO.getUsername();
        validator.validateUserExists(username);
        log.info("[{}] Attempting to toggle activation for user: {}", transactionId, username);

        User user = userRepo.findByUsername(username);
        user.setIsActive(toggleActivationDTO.getIsActive());
        userRepo.save(user);
        log.info("[{}] Successfully toggled activation for user: {}. Now active: {}", transactionId, username, user.getIsActive());
    }
}
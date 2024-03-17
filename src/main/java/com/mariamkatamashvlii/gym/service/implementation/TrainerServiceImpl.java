package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.auth.Validator;
import com.mariamkatamashvlii.gym.dto.RegistrationDTO;
import com.mariamkatamashvlii.gym.dto.TraineeDTO;
import com.mariamkatamashvlii.gym.dto.TrainerProfileDTO;
import com.mariamkatamashvlii.gym.dto.TrainingDTO;
import com.mariamkatamashvlii.gym.dto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.dto.UpdateTrainerDTO;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepo;
    private final UserRepository userRepo;
    private final TrainingTypeRepository trainingTypeRepo;
    private final Validator validation;
    private final UsernameGenerator usernameGenerator;
    private final PasswordGenerator passwordGenerator;

    @Override
    public RegistrationDTO registerTrainer(String firstName, String lastName, Long trainingTypeId) {
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(usernameGenerator.generateUsername(firstName, lastName))
                .password(passwordGenerator.generatePassword())
                .isActive(true)
                .build();
        userRepo.create(user);
        TrainingType type = trainingTypeRepo.select(trainingTypeId);
        Trainer trainer = Trainer.builder()
                .specialization(type)
                .user(user)
                .build();
        trainerRepo.create(trainer);
        validation.validateTrainer(trainer, user);
        return new RegistrationDTO(user.getUsername(), user.getPassword());
    }

    @Override
    public TrainerProfileDTO trainerProfile(String username) {
        Trainer trainer = trainerRepo.select(username);
        if (trainer == null) {
            throw new EntityNotFoundException("Trainer not found with username - " + username);
        }
        TrainingTypeDTO specialization = new TrainingTypeDTO(
                trainer.getSpecialization().getId(),
                trainer.getSpecialization().getTrainingTypeName()
        );
        List<TraineeDTO> trainees = trainer.getTrainees().stream().map(trainee -> new TraineeDTO(
                trainee.getUser().getUsername(),
                trainee.getUser().getFirstName(),
                trainee.getUser().getLastName()
        )).toList();
        return new TrainerProfileDTO(
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),
                specialization,
                trainer.getUser().getIsActive(),
                trainees
        );
    }

    @Override
    public UpdateTrainerDTO updateProfile(String username, String firstName, String lastName, TrainingTypeDTO specialization, Boolean isActive) {
        User user = userRepo.select(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setIsActive(isActive);
        userRepo.update(user);
        Trainer trainer = trainerRepo.select(username);
        if (trainer == null) {
            throw new EntityNotFoundException("Trainee not found");
        }
        List<TraineeDTO> trainees = trainer.getTrainees().stream().map(trainee -> new TraineeDTO(
                trainee.getUser().getUsername(),
                trainee.getUser().getFirstName(),
                trainee.getUser().getLastName()
        )).toList();
        return new UpdateTrainerDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                specialization,
                user.getIsActive(),
                trainees
        );
    }

    @Override
    public List<TrainingDTO> getTrainings(String username, Date fromDate, Date toDate, String traineeName) {
        Trainer trainer = trainerRepo.select(username);
        if (trainer == null || trainer.getTrainings() == null) {
            log.info("No trainings found or trainer does not exist for username: {}", username);
            return List.of();
        }
        return trainer.getTrainings().stream()
                .filter(t -> (fromDate == null || !t.getTrainingDate().before(fromDate)) && (toDate == null || !t.getTrainingDate().after(toDate)))
                .filter(t -> traineeName == null || t.getTrainee().getUser().getUsername().equalsIgnoreCase(traineeName))
                .map(t -> {
                    TrainingDTO dto = new TrainingDTO();
                    dto.setTrainingName(t.getTrainingName());
                    dto.setDate(t.getTrainingDate());
                    dto.setTrainingType(t.getTrainingType());
                    dto.setDuration(t.getDuration());
                    dto.setName(t.getTrainer().getUser().getUsername());
                    return dto;
                }).toList();
    }

    @Override
    public void activateTrainer(String username, Boolean isActive) {
        toggleActivation(username, isActive);
        log.info("Set activation to true for - {}", username);
    }

    @Override
    public void deactivateTrainer(String username, Boolean isActive) {
        toggleActivation(username, isActive);
        log.info("Set activation to false for - {}", username);
    }

    private void toggleActivation(String username, boolean isActive) {
        User user = userRepo.select(username);
        user.setIsActive(isActive);
        userRepo.update(user);
    }
}

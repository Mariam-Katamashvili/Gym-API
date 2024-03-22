package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.auth.Validator;
import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.TraineeDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainerTrainingsRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
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

import java.time.LocalDate;
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
    public RegistrationResponseDTO registerTrainer(RegistrationRequestDTO registrationRequestDTO) {
        User user = User.builder()
                .firstName(registrationRequestDTO.getFirstName())
                .lastName(registrationRequestDTO.getLastName())
                .username(usernameGenerator.generateUsername(registrationRequestDTO.getFirstName(), registrationRequestDTO.getLastName()))
                .password(passwordGenerator.generatePassword())
                .isActive(true)
                .build();
        userRepo.save(user);
        TrainingType type = trainingTypeRepo.findById(registrationRequestDTO.getSpecialization().getTrainingTypeId()).orElseThrow(() -> new EntityNotFoundException("TrainingType not found for id: " + registrationRequestDTO.getSpecialization().getTrainingTypeId()));
        Trainer trainer = Trainer.builder()
                .specialization(type)
                .user(user)
                .build();
        trainerRepo.save(trainer);
        validation.validateTrainer(trainer, user);
        return new RegistrationResponseDTO(user.getUsername(), user.getPassword());
    }

    @Override
    public ProfileResponseDTO getTrainerProfile(String username) {
        Trainer trainer = trainerRepo.findByUsername(username);
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
        return new ProfileResponseDTO(
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),
                specialization,
                trainer.getUser().getIsActive(),
                trainees
        );
    }

    @Override
    public UpdateResponseDTO updateProfile(UpdateRequestDTO updateRequestDTO) {
        String username = updateRequestDTO.getUsername();
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        Trainer trainer = trainerRepo.findByUsername(username);
        if (trainer == null) {
            throw new EntityNotFoundException("Trainee not found");
        }
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
    public List<TrainingResponseDTO> getTrainings(TrainerTrainingsRequestDTO trainerTrainingsRequestDTO) {
        String username = trainerTrainingsRequestDTO.getUsername();
        LocalDate fromDate = trainerTrainingsRequestDTO.getFromDate();
        LocalDate toDate = trainerTrainingsRequestDTO.getToDate();
        String traineeName = trainerTrainingsRequestDTO.getTraineeName();
        Trainer trainer = trainerRepo.findByUsername(username);
        if (trainer == null || trainer.getTrainings() == null) {
            log.info("No trainings found or trainer does not exist for username: {}", username);
            return List.of();
        }
        return trainer.getTrainings().stream()
                .filter(t -> (fromDate == null || !t.getTrainingDate().isBefore(fromDate)) && (toDate == null || !t.getTrainingDate().isAfter(toDate)))
                .filter(t -> traineeName == null || t.getTrainee().getUser().getUsername().equalsIgnoreCase(traineeName))
                .map(t -> {
                    TrainingResponseDTO dto = new TrainingResponseDTO();
                    dto.setTrainingName(t.getTrainingName());
                    dto.setDate(t.getTrainingDate());
                    dto.setTrainingType(t.getTrainingType());
                    dto.setDuration(t.getDuration());
                    dto.setName(t.getTrainer().getUser().getUsername());
                    return dto;
                }).toList();
    }

    @Override
    public void toggleActivation(ToggleActivationDTO toggleActivationDTO) {
        User user = userRepo.findByUsername(toggleActivationDTO.getUsername());
        user.setIsActive(toggleActivationDTO.getIsActive());
        userRepo.save(user);
    }
}

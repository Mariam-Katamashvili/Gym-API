package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateTrainersRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerUsenameDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TraineeTrainingsRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.exception.UserNotCreatedException;
import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.service.TraineeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Override
    @Transactional
    public RegistrationResponseDTO registerTrainee(RegistrationRequestDTO registrationRequestDTO) {
        try {
            String firstName = registrationRequestDTO.getFirstName();
            String lastName = registrationRequestDTO.getLastName();
            User user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(usernameGenerator.generateUsername(firstName, lastName))
                    .password(passwordGenerator.generatePassword())
                    .isActive(true)
                    .build();
            userRepo.save(user);

            Trainee trainee = Trainee.builder()
                    .birthday(registrationRequestDTO.getBirthday())
                    .address(registrationRequestDTO.getAddress())
                    .user(user)
                    .build();
            traineeRepo.save(trainee);
            log.info("Registered new trainee with username: {}", user.getUsername());
            return new RegistrationResponseDTO(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            throw new UserNotCreatedException("Could not create trainee");
        }
    }

    @Override
    public ProfileResponseDTO getTraineeProfile(String username) {
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
        User user = userRepo.findByUsername(updateRequestDTO.getUsername());
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        user.setFirstName(updateRequestDTO.getFirstName());
        user.setLastName(updateRequestDTO.getLastName());
        user.setIsActive(updateRequestDTO.getIsActive());
        userRepo.save(user);
        Trainee trainee = traineeRepo.findByUsername(updateRequestDTO.getUsername());
        if (trainee == null) {
            throw new EntityNotFoundException("Trainee not found");
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
    public void delete(String username) {
        Trainee trainee = traineeRepo.findByUsername(username);
        User user = userRepo.findByUsername(username);
        if (trainee == null) {
            throw new EntityNotFoundException("trainee does not exist");
        }
        Set<Training> trainings = trainee.getTrainings();
        for (Training t : trainings) {
            trainingRepository.delete(t);
        }
        traineeRepo.delete(trainee);
        log.info("Deleted trainee with username {}", username);
        userRepo.delete(user);
        log.info("Deleted user with username {}", username);
    }

    @Override
    public List<TrainerDTO> getUnassignedTrainers(String username) {
        List<TrainerDTO> unassignedTrainers = new ArrayList<>();
        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee != null) {
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
        }
        log.info("Returning not assigned trainer list for trainee {}", username);
        return unassignedTrainers;
    }

    @Override
    public List<TrainingResponseDTO> getTrainings(TraineeTrainingsRequestDTO traineeTrainingsRequestDTO) {
        String username = traineeTrainingsRequestDTO.getUsername();
        String trainerName = traineeTrainingsRequestDTO.getTrainerName();
        LocalDate fromDate = traineeTrainingsRequestDTO.getFromDate(); // Assuming these fields exist
        LocalDate toDate = traineeTrainingsRequestDTO.getToDate();
        TrainingTypeDTO trainingType = traineeTrainingsRequestDTO.getTrainingType();
        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null || trainee.getTrainings() == null) {
            log.info("No trainings found or trainee does not exist for username: {}", username);
            return List.of();
        }

        return trainee.getTrainings().stream()
                .filter(t -> (fromDate == null || !t.getTrainingDate().isBefore(fromDate)) && (toDate == null || !t.getTrainingDate().isAfter(toDate)))
                .filter(t -> trainerName == null || t.getTrainer().getUser().getUsername().equalsIgnoreCase(trainerName))
                .filter(t -> trainingType == null || t.getTrainingType().getTrainingTypeName().equals(trainingType.getTrainingTypeName()))
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
    public List<TrainerDTO> updateTrainers(UpdateTrainersRequestDTO updateTrainersRequestDTO) {
        String username = updateTrainersRequestDTO.getUsername();
        Trainee trainee = traineeRepo.findByUsername(username);
        List<TrainerDTO> newTrainers = new ArrayList<>();
        if (trainee != null) {
            List<Trainer> updatedTrainers = updateTrainersRequestDTO.getTrainers().stream()
                    .map(TrainerUsenameDTO::getUsername)
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

            log.info("Updated trainer list for trainee - {}", username);
        }
        return newTrainers;
    }

    @Override
    public void toggleActivation(ToggleActivationDTO toggleActivationDTO) {
        User user = userRepo.findByUsername(toggleActivationDTO.getUsername());
        user.setIsActive(toggleActivationDTO.getIsActive());
        userRepo.save(user);
    }
}

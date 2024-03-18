package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.TraineeProfileDTO;
import com.mariamkatamashvlii.gym.dto.TrainerDTO;
import com.mariamkatamashvlii.gym.dto.TrainerUsenameDTO;
import com.mariamkatamashvlii.gym.dto.TrainingDTO;
import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.dto.UpdateTraineeDTO;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.entity.TrainingType;
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

import java.sql.Date;
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
    public RegistrationResponseDTO registerTrainee(TraineeProfileDTO traineeProfileDTO) {
        try {
            String firstName = traineeProfileDTO.getFirstName();
            String lastName = traineeProfileDTO.getLastName();
            User user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(usernameGenerator.generateUsername(firstName, lastName))
                    .password(passwordGenerator.generatePassword())
                    .isActive(true)
                    .build();
            userRepo.save(user);

            Trainee trainee = Trainee.builder()
                    .birthday(traineeProfileDTO.getBirthday())
                    .address(traineeProfileDTO.getAddress())
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
    public TraineeProfileDTO getTraineeProfile(String username) {
        User user = userRepo.findUserByUsername(username);
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
        return new TraineeProfileDTO(
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
    public UpdateTraineeDTO updateProfile(String username, String firstName, String lastName, Date birthday, String address, Boolean isActive) {
        User user = userRepo.findUserByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setIsActive(isActive);
        userRepo.save(user);
        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null) {
            throw new EntityNotFoundException("Trainee not found");
        }
        trainee.setBirthday(birthday);
        trainee.setAddress(address);
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
        return new UpdateTraineeDTO(
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
        if (trainee == null) {
            log.info("Trainee does not exist for username: {}", username);
            return;
        }
        Set<Training> trainings = trainee.getTrainings();
        for (Training t : trainings) {
            trainingRepository.delete(t.getId());
        }
        traineeRepo.delete(trainee);
        log.info("Deleted trainee with username {}", username);
    }

    @Override
    public List<TrainerDTO> getNotAssignedTrainers(String username) {
        List<TrainerDTO> notAssignedTrainers = new ArrayList<>();
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
                    notAssignedTrainers.add(dto);
                }
            }
        }
        log.info("Returning not assigned trainer list for trainee {}", username);
        return notAssignedTrainers;
    }

    @Override
    public List<TrainingDTO> getTrainings(String username, Date fromDate, Date toDate, String trainerName, TrainingType trainingType) {
        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null || trainee.getTrainings() == null) {
            log.info("No trainings found or trainee does not exist for username: {}", username);
            return List.of();
        }

        return trainee.getTrainings().stream()
                .filter(t -> (fromDate == null || !t.getTrainingDate().before(fromDate)) && (toDate == null || !t.getTrainingDate().after(toDate)))
                .filter(t -> trainerName == null || t.getTrainer().getUser().getUsername().equalsIgnoreCase(trainerName))
                .filter(t -> trainingType == null || t.getTrainingType().equals(trainingType))
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
    public List<TrainerDTO> updateTrainers(String username, List<TrainerUsenameDTO> trainers) {
        Trainee trainee = traineeRepo.findByUsername(username);
        List<TrainerDTO> newTrainers = new ArrayList<>();
        if (trainee != null) {
            List<Trainer> updatedTrainers = trainers.stream()
                    .map(TrainerUsenameDTO::getUsername)
                    .map(trainerRepo::select)
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
    public void activateTrainee(String username, Boolean isActive) {
        toggleActivation(username, true);
        log.info("Set activation to true for - {}", username);
    }

    @Override
    public void deactivateTrainee(String username, Boolean isActive) {
        toggleActivation(username, false);
        log.info("Set activation to false for - {}", username);
    }

    private void toggleActivation(String username, Boolean isActive) {
        User user = userRepo.findUserByUsername(username);
        user.setIsActive(isActive);
        userRepo.save(user);
    }
}

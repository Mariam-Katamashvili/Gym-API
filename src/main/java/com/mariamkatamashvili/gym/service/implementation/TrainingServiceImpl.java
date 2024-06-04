package com.mariamkatamashvili.gym.service.implementation;

import com.mariamkatamashvili.gym.dto.WorkloadDTO;
import com.mariamkatamashvili.gym.dto.trainingDto.TrainingRequestDTO;
import com.mariamkatamashvili.gym.entity.Trainee;
import com.mariamkatamashvili.gym.entity.Trainer;
import com.mariamkatamashvili.gym.entity.Training;
import com.mariamkatamashvili.gym.exception.GymException;
import com.mariamkatamashvili.gym.service.TrainingService;
import com.mariamkatamashvili.gym.dto.ActionType;
import com.mariamkatamashvili.gym.messaging.MessageProducer;
import com.mariamkatamashvili.gym.repository.TraineeRepository;
import com.mariamkatamashvili.gym.repository.TrainerRepository;
import com.mariamkatamashvili.gym.repository.TrainingRepository;
import com.mariamkatamashvili.gym.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepo;
    private final TraineeRepository traineeRepo;
    private final TrainerRepository trainerRepo;
    private final Validator validator;
    private final MessageProducer messageProducer;

    private static final String TRAINEE_NOT_FOUND = "Trainee not found";

    @Override
    @Transactional
    @PreAuthorize("#trainingRequest.traineeUsername == authentication.principal.username")
    public void create(TrainingRequestDTO trainingRequest) {
        validator.validateTraineeExists(trainingRequest.getTraineeUsername());
        validator.validateTrainerExists(trainingRequest.getTrainerUsername());
        validator.validateFutureDate(trainingRequest.getDate());

        Trainee trainee = traineeRepo.findByUsername(trainingRequest.getTraineeUsername());
        Trainer trainer = trainerRepo.findByUsername(trainingRequest.getTrainerUsername());

        Training training = Training.builder()
                .trainee(trainee)
                .trainer(trainer)
                .trainingName(trainingRequest.getTrainingName())
                .trainingDate(trainingRequest.getDate())
                .duration(trainingRequest.getDuration())
                .trainingType(trainer.getSpecialization())
                .build();

        trainingRepo.save(training);

        WorkloadDTO workload = WorkloadDTO.builder()
                .username(trainer.getUser().getUsername())
                .firstName(trainer.getUser().getFirstName())
                .lastName(trainer.getUser().getLastName())
                .isActive(trainer.getUser().getIsActive())
                .date(training.getTrainingDate())
                .duration(training.getDuration())
                .actionType(ActionType.ADD)
                .build();
        messageProducer.sendMessage(workload);
    }

    @Override
    @Transactional
    public void removeTrainings(String username) {
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
            messageProducer.sendMessage(workload);
        }
    }
}
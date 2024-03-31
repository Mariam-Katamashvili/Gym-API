package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingRequestDTO;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.service.TrainingService;
import com.mariamkatamashvlii.gym.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepo;
    private final TraineeRepository traineeRepo;
    private final TrainerRepository trainerRepo;
    private final Validator validator;

    @Override
    @Transactional
    public void create(TrainingRequestDTO trainingRequestDTO) {
        String transactionId = UUID.randomUUID().toString();
        validator.validateTraineeExists(trainingRequestDTO.getTraineeUsername());
        validator.validateTrainerExists(trainingRequestDTO.getTrainerUsername());
        log.info("[{}] Creating training", transactionId);

        Trainee trainee = traineeRepo.findByUsername(trainingRequestDTO.getTraineeUsername());
        Trainer trainer = trainerRepo.findByUsername(trainingRequestDTO.getTrainerUsername());

        Training training = Training.builder()
                .trainee(trainee)
                .trainer(trainer)
                .trainingName(trainingRequestDTO.getTrainingName())
                .trainingDate(trainingRequestDTO.getDate())
                .duration(trainingRequestDTO.getDuration())
                .build();

        trainingRepo.save(training);
        log.info("[{}] Training created successfully", transactionId);
    }
}
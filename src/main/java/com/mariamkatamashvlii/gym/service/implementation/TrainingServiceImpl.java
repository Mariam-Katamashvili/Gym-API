package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.client.WorkloadServiceClient;
import com.mariamkatamashvlii.gym.dto.ActionType;
import com.mariamkatamashvlii.gym.dto.WorkloadDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingRequestDTO;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.service.TrainingService;
import com.mariamkatamashvlii.gym.validator.Validator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepo;
    private final TraineeRepository traineeRepo;
    private final TrainerRepository trainerRepo;
    private final Validator validator;
    private final WorkloadServiceClient workloadServiceClient;

    @Override
    @Transactional
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
        workloadServiceClient.sendWorkload(workload);
    }
}
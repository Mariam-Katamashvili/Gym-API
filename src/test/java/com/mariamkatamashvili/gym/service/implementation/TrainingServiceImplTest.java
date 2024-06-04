package com.mariamkatamashvili.gym.service.implementation;

import com.mariamkatamashvili.gym.dto.ActionType;
import com.mariamkatamashvili.gym.dto.WorkloadDTO;
import com.mariamkatamashvili.gym.dto.trainingDto.TrainingRequestDTO;
import com.mariamkatamashvili.gym.entity.Trainee;
import com.mariamkatamashvili.gym.entity.Trainer;
import com.mariamkatamashvili.gym.entity.Training;
import com.mariamkatamashvili.gym.entity.TrainingType;
import com.mariamkatamashvili.gym.entity.User;
import com.mariamkatamashvili.gym.exception.GymException;
import com.mariamkatamashvili.gym.repository.TraineeRepository;
import com.mariamkatamashvili.gym.repository.TrainerRepository;
import com.mariamkatamashvili.gym.repository.TrainingRepository;
import com.mariamkatamashvili.gym.validator.Validator;
import com.mariamkatamashvili.gym.messaging.MessageProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    private static final String TRAINEE_USERNAME = "traineeName";
    private static final String TRAINER_USERNAME = "trainerName";
    private static final String TRAINING_NAME = "trainingName";
    private static final LocalDate TRAINING_DATE = LocalDate.of(2000, 1, 1);
    private static final Integer DURATION = 30;
    private static final String TRAINER_SPECIALIZATION = "yoga";
    private static final String ERROR_MESSAGE = "Trainer not found with username: " + TRAINER_USERNAME;
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final Boolean IS_ACTIVE = true;

    @Mock
    private TrainingRepository trainingRepo;
    @Mock
    private TraineeRepository traineeRepo;
    @Mock
    private TrainerRepository trainerRepo;
    @Mock
    private Validator validator;
    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Test
    @Transactional
    void createTraining() {
        // given
        TrainingType trainingType = TrainingType.builder().trainingTypeName(TRAINER_SPECIALIZATION).build();
        Trainee trainee = new Trainee();
        Trainer trainer = Trainer.builder()
                .specialization(trainingType)
                .user(User.builder()
                        .username(TRAINER_USERNAME)
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .isActive(IS_ACTIVE)
                        .build())
                .build();

        TrainingRequestDTO trainingRequest = TrainingRequestDTO.builder()
                .traineeUsername(TRAINEE_USERNAME)
                .trainerUsername(TRAINER_USERNAME)
                .trainingName(TRAINING_NAME)
                .date(TRAINING_DATE)
                .duration(DURATION)
                .build();

        when(traineeRepo.findByUsername(anyString())).thenReturn(trainee);
        when(trainerRepo.findByUsername(anyString())).thenReturn(trainer);

        // when
        trainingService.create(trainingRequest);

        // then
        ArgumentCaptor<Training> trainingCaptor = ArgumentCaptor.forClass(Training.class);
        verify(trainingRepo).save(trainingCaptor.capture());

        Training capturedTraining = trainingCaptor.getValue();

        Assertions.assertEquals(TRAINING_NAME, capturedTraining.getTrainingName());
        Assertions.assertEquals(trainingType, trainer.getSpecialization());
        Assertions.assertEquals(TRAINING_DATE, capturedTraining.getTrainingDate());
        Assertions.assertEquals(DURATION, capturedTraining.getDuration());
        Assertions.assertSame(trainee, capturedTraining.getTrainee());
        Assertions.assertSame(trainer, capturedTraining.getTrainer());

        ArgumentCaptor<WorkloadDTO> workloadCaptor = ArgumentCaptor.forClass(WorkloadDTO.class);
        verify(messageProducer).sendMessage(workloadCaptor.capture());

        WorkloadDTO capturedWorkload = workloadCaptor.getValue();
        Assertions.assertEquals(TRAINER_USERNAME, capturedWorkload.getUsername());
        Assertions.assertEquals(FIRST_NAME, capturedWorkload.getFirstName());
        Assertions.assertEquals(LAST_NAME, capturedWorkload.getLastName());
        Assertions.assertEquals(IS_ACTIVE, capturedWorkload.getIsActive());
        Assertions.assertEquals(TRAINING_DATE, capturedWorkload.getDate());
        Assertions.assertEquals(DURATION, capturedWorkload.getDuration());
        Assertions.assertEquals(ActionType.ADD, capturedWorkload.getActionType());
    }

    @Test
    @Transactional
    void createTraining_WhenTrainerDoesNotExist_ThenThrowException() {
        // given
        TrainingRequestDTO trainingRequest = TrainingRequestDTO.builder()
                .traineeUsername(TRAINEE_USERNAME)
                .trainerUsername(TRAINER_USERNAME)
                .trainingName(TRAINING_NAME)
                .date(TRAINING_DATE)
                .duration(DURATION)
                .build();

        when(trainerRepo.findByUsername(anyString())).thenThrow(new GymException(ERROR_MESSAGE));

        // when
        GymException exception = Assertions.assertThrows(
                GymException.class,
                () -> trainingService.create(trainingRequest)
        );

        // then
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(ERROR_MESSAGE, exception.getMessage());

        verify(trainerRepo, times(1)).findByUsername(TRAINER_USERNAME);
    }

    @Test
    @Transactional
    void removeTrainings() {
        // given
        LocalDate now = LocalDate.now();
        Trainee trainee = new Trainee();
        trainee.setTrainings(new HashSet<>());
        Trainer trainer = Trainer.builder()
                .user(User.builder()
                        .username(TRAINER_USERNAME)
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .isActive(IS_ACTIVE)
                        .build())
                .build();
        Training training = Training.builder()
                .trainee(trainee)
                .trainer(trainer)
                .trainingDate(now.plusDays(1))
                .duration(DURATION)
                .build();
        trainee.getTrainings().add(training);

        when(traineeRepo.findByUsername(anyString())).thenReturn(trainee);

        // when
        trainingService.removeTrainings(TRAINEE_USERNAME);

        // then
        ArgumentCaptor<WorkloadDTO> workloadCaptor = ArgumentCaptor.forClass(WorkloadDTO.class);
        verify(messageProducer).sendMessage(workloadCaptor.capture());

        WorkloadDTO capturedWorkload = workloadCaptor.getValue();
        Assertions.assertEquals(TRAINER_USERNAME, capturedWorkload.getUsername());
        Assertions.assertEquals(FIRST_NAME, capturedWorkload.getFirstName());
        Assertions.assertEquals(LAST_NAME, capturedWorkload.getLastName());
        Assertions.assertEquals(IS_ACTIVE, capturedWorkload.getIsActive());
        Assertions.assertEquals(now.plusDays(1), capturedWorkload.getDate());
        Assertions.assertEquals(DURATION, capturedWorkload.getDuration());
        Assertions.assertEquals(ActionType.DELETE, capturedWorkload.getActionType());
    }

    @Test
    @Transactional
    void removeTrainings_WhenTraineeDoesNotExist_ThenThrowException() {
        // given
        when(traineeRepo.findByUsername(anyString())).thenReturn(null);

        // when
        GymException exception = Assertions.assertThrows(
                GymException.class,
                () -> trainingService.removeTrainings(TRAINEE_USERNAME)
        );

        // then
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Trainee not found", exception.getMessage());

        verify(traineeRepo, times(1)).findByUsername(TRAINEE_USERNAME);
    }
}
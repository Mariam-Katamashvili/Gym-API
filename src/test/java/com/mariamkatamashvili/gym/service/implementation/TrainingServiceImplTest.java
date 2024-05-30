//package com.mariamkatamashvlii.gym.service.implementation;
//
//import com.mariamkatamashvlii.gym.client.WorkloadServiceClient;
//import com.mariamkatamashvlii.gym.dto.WorkloadDTO;
//import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingRequestDTO;
//import com.mariamkatamashvlii.gym.entity.Trainee;
//import com.mariamkatamashvlii.gym.entity.Trainer;
//import com.mariamkatamashvlii.gym.entity.Training;
//import com.mariamkatamashvlii.gym.entity.TrainingType;
//import com.mariamkatamashvlii.gym.entity.User;
//import com.mariamkatamashvlii.gym.exception.GymException;
//import com.mariamkatamashvlii.gym.repository.TraineeRepository;
//import com.mariamkatamashvlii.gym.repository.TrainerRepository;
//import com.mariamkatamashvlii.gym.repository.TrainingRepository;
//import com.mariamkatamashvlii.gym.validator.Validator;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class TrainingServiceImplTest {
//    private static final String TRAINEE_USERNAME = "traineeName";
//    private static final String TRAINER_USERNAME = "trainerName";
//    private static final String TRAINING_NAME = "trainingName";
//    private static final LocalDate TRAINING_DATE = LocalDate.of(2000, 1, 1);
//    private static final Integer DURATION = 30;
//    private static final String TRAINER_SPECIALIZATION = "yoga";
//    private static final String ERROR_MESSAGE = "Trainer not found with username: " + TRAINER_USERNAME;
//    private static final String FIRST_NAME = "FirstName";
//    private static final String LAST_NAME = "LastName";
//    private static final Boolean IS_ACTIVE = true;
//
//    @Mock
//    private TrainingRepository trainingRepo;
//    @Mock
//    private TraineeRepository traineeRepo;
//    @Mock
//    private TrainerRepository trainerRepo;
//    @Mock
//    private Validator validator;
//    @Mock
//    private WorkloadServiceClient workloadServiceClient;
//
//    @InjectMocks
//    private TrainingServiceImpl trainingService;
//
//    @Test
//    @Transactional
//    void createTraining() {
//        // given
//        TrainingType trainingType = TrainingType.builder().trainingTypeName(TRAINER_SPECIALIZATION).build();
//        Trainee trainee = new Trainee();
//        Trainer trainer = Trainer.builder()
//                .specialization(trainingType)
//                .user(User.builder()
//                        .username(TRAINER_USERNAME)
//                        .firstName(FIRST_NAME)
//                        .lastName(LAST_NAME)
//                        .isActive(IS_ACTIVE)
//                        .build())
//                .build();
//
//        TrainingRequestDTO trainingRequest = TrainingRequestDTO.builder()
//                .traineeUsername(TRAINEE_USERNAME)
//                .trainerUsername(TRAINER_USERNAME)
//                .trainingName(TRAINING_NAME)
//                .date(TRAINING_DATE)
//                .duration(DURATION)
//                .build();
//
//        when(traineeRepo.findByUsername(anyString())).thenReturn(trainee);
//        when(trainerRepo.findByUsername(anyString())).thenReturn(trainer);
//
//        // when
//        trainingService.create(trainingRequest);
//
//        // then
//        ArgumentCaptor<Training> trainingCaptor = ArgumentCaptor.forClass(Training.class);
//        verify(trainingRepo).save(trainingCaptor.capture());
//
//        Training capturedTraining = trainingCaptor.getValue();
//
//        Assertions.assertEquals(TRAINING_NAME, capturedTraining.getTrainingName());
//        Assertions.assertEquals(trainingType, trainer.getSpecialization());
//        Assertions.assertEquals(TRAINING_DATE, capturedTraining.getTrainingDate());
//        Assertions.assertEquals(DURATION, capturedTraining.getDuration());
//        Assertions.assertSame(trainee, capturedTraining.getTrainee());
//        Assertions.assertSame(trainer, capturedTraining.getTrainer());
//
//        verify(workloadServiceClient, times(1)).sendWorkload(any(WorkloadDTO.class));
//    }
//
//    @Test
//    @Transactional
//    void createTraining_WhenTrainerDoesNotExist_ThenThrowException() {
//        // given
//        TrainingRequestDTO trainingRequest = TrainingRequestDTO.builder()
//                .traineeUsername(TRAINEE_USERNAME)
//                .trainerUsername(TRAINER_USERNAME)
//                .trainingName(TRAINING_NAME)
//                .date(TRAINING_DATE)
//                .duration(DURATION)
//                .build();
//
//        when(trainerRepo.findByUsername(anyString())).thenThrow(new GymException(ERROR_MESSAGE));
//
//        // when
//        GymException exception = Assertions.assertThrows(
//                GymException.class,
//                () -> trainingService.create(trainingRequest)
//        );
//
//        // then
//        Assertions.assertNotNull(exception);
//        Assertions.assertEquals(ERROR_MESSAGE, exception.getMessage());
//
//        verify(trainerRepo, times(1)).findByUsername(TRAINER_USERNAME);
//    }
//}
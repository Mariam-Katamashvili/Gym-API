package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingRequestDTO;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.exception.TraineeNotFoundException;
import com.mariamkatamashvlii.gym.exception.TrainerNotFoundException;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainingServiceImplTest {
    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private static final String TRAINEE_USERNAME = "testTrainee";
    private static final String TRAINER_USERNAME = "testTrainer";
    private static final String TRAINING_NAME = "Strength Training";
    private static final LocalDate TRAINING_DATE = LocalDate.of(2024, 1, 1);
    private static final Integer TRAINING_DURATION = 60;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTraining_Success() {
        // Given
        TrainingRequestDTO dto = new TrainingRequestDTO(TRAINEE_USERNAME, TRAINER_USERNAME, TRAINING_NAME, TRAINING_DATE, TRAINING_DURATION);
        Trainee mockTrainee = new Trainee();
        Trainer mockTrainer = new Trainer();
        when(traineeRepository.findByUsername(dto.getTraineeUsername())).thenReturn(mockTrainee);
        when(trainerRepository.findByUsername(dto.getTrainerUsername())).thenReturn(mockTrainer);
        doNothing().when(validator).validateTraineeExists(dto.getTraineeUsername());
        doNothing().when(validator).validateTrainerExists(dto.getTrainerUsername());

        // When
        trainingService.create(dto);

        // Then
        verify(trainingRepository, times(1)).save(any());
    }

    @Test
    void createTraining_Fail_TraineeNotFound() {
        // Given
        TrainingRequestDTO dto = new TrainingRequestDTO(TRAINEE_USERNAME, TRAINER_USERNAME, TRAINING_NAME, TRAINING_DATE, TRAINING_DURATION);
        doThrow(new TraineeNotFoundException("Trainee not found for username - " + TRAINEE_USERNAME)).when(validator).validateTraineeExists(dto.getTraineeUsername());

        // When & Then
        Exception exception = assertThrows(TraineeNotFoundException.class, () -> trainingService.create(dto));
        assertTrue(exception.getMessage().contains("Trainee not found for username - " + TRAINEE_USERNAME));
    }

    @Test
    void createTraining_Fail_TrainerNotFound() {
        // Given
        TrainingRequestDTO dto = new TrainingRequestDTO(TRAINEE_USERNAME, TRAINER_USERNAME, TRAINING_NAME, TRAINING_DATE, TRAINING_DURATION);
        doNothing().when(validator).validateTraineeExists(dto.getTraineeUsername());
        doThrow(new TrainerNotFoundException("Trainer not found")).when(validator).validateTrainerExists(dto.getTrainerUsername());

        // When & Then
        Exception exception = assertThrows(TrainerNotFoundException.class, () -> trainingService.create(dto));
        assertTrue(exception.getMessage().contains("Trainer not found"));
    }
}

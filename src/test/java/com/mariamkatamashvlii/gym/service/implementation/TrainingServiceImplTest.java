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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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

    @Test
    void whenCreateTrainingAndAllValid_thenTrainingIsCreated() {
        // Given
        TrainingRequestDTO requestDTO = new TrainingRequestDTO(TRAINEE_USERNAME, TRAINER_USERNAME, TRAINING_NAME, TRAINING_DATE, TRAINING_DURATION);
        Trainee mockTrainee = new Trainee();
        Trainer mockTrainer = new Trainer();
        when(traineeRepository.findByUsername(requestDTO.getTraineeUsername())).thenReturn(mockTrainee);
        when(trainerRepository.findByUsername(requestDTO.getTrainerUsername())).thenReturn(mockTrainer);
        doNothing().when(validator).validateTraineeExists(requestDTO.getTraineeUsername());
        doNothing().when(validator).validateTrainerExists(requestDTO.getTrainerUsername());

        // When
        trainingService.create(requestDTO);

        // Then
        verify(trainingRepository, times(1)).save(any());
    }

    @Test
    void whenCreateTrainingAndTraineeNotFound_thenThrowsException() {
        // Given
        TrainingRequestDTO requestDTO = new TrainingRequestDTO(TRAINEE_USERNAME, TRAINER_USERNAME, TRAINING_NAME, TRAINING_DATE, TRAINING_DURATION);
        doThrow(new TraineeNotFoundException("Trainee not found for username - " + TRAINEE_USERNAME)).when(validator).validateTraineeExists(requestDTO.getTraineeUsername());

        // When & Then
        assertThatThrownBy(() -> trainingService.create(requestDTO))
                .isInstanceOf(TraineeNotFoundException.class)
                .hasMessageContaining("Trainee not found for username - " + TRAINEE_USERNAME);
    }

    @Test
    void whenCreateTrainingAndTrainerNotFound_thenThrowsException() {
        // Given
        TrainingRequestDTO requestDTO = new TrainingRequestDTO(TRAINEE_USERNAME, TRAINER_USERNAME, TRAINING_NAME, TRAINING_DATE, TRAINING_DURATION);
        doNothing().when(validator).validateTraineeExists(requestDTO.getTraineeUsername());
        doThrow(new TrainerNotFoundException("Trainer not found")).when(validator).validateTrainerExists(requestDTO.getTrainerUsername());

        // When & Then
        assertThatThrownBy(() -> trainingService.create(requestDTO))
                .isInstanceOf(TrainerNotFoundException.class)
                .hasMessageContaining("Trainer not found");
    }
}
package com.mariamkatamashvlii.gym.serviceImplTests;

import com.mariamkatamashvlii.gym.daoImpl.TrainingDaoImpl;
import com.mariamkatamashvlii.gym.model.Training;
import com.mariamkatamashvlii.gym.model.TrainingType;
import com.mariamkatamashvlii.gym.serviceImpl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class TrainingServiceImplTest {
    @Mock
    private TrainingDaoImpl trainingDaoImpl;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private Training training;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TrainingType trainingType = new TrainingType("Yoga");
        training = new Training(1L, 2L, "YogaSession", trainingType, LocalDate.now(), 1.5f);
    }

    @Test
    void testCreateTraining() {
        trainingService.create(training);
        verify(trainingDaoImpl, times(1)).create(training);
    }

    @Test
    void testSelectTraining() {
        String trainingName = training.getTrainingName();
        when(trainingDaoImpl.select(trainingName)).thenReturn(training);
        Training result = trainingService.select(trainingName);
        assertEquals(training, result);
        verify(trainingDaoImpl, times(1)).select(trainingName);
    }

}

package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.repository.TrainingRepository;
import com.mariamkatamashvlii.gym.service.serviceImplementation.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainingServiceImplTest {
    @Mock
    private TrainingRepository trainingRepo;
    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void create() {
//        Training training = new Training();
//        training.setId(1L);
//        when(trainingRepo.create(training)).thenReturn(training);
//
//        Training createdTraining = trainingService.create();
//
//        assertNotNull(createdTraining, "The created training should not be null.");
//        verify(trainingRepo).create(training);
//    }


}

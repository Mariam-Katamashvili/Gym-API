//package com.mariamkatamashvlii.gym.serviceImplementation;
//
//import com.mariamkatamashvlii.gym.entity.Training;
//import com.mariamkatamashvlii.gym.repository.TrainingRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class TrainingServiceImplTest {
//
//    @Mock
//    private TrainingRepository trainingRepo;
//
//    @InjectMocks
//    private TrainingServiceImpl trainingService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void create() {
//        Training training = new Training();
//        training.setId(1L);
//        doNothing().when(trainingRepo).create(training);
//
//        trainingService.create(training);
//
//        verify(trainingRepo).create(training);
//    }
//
//    @Test
//    void update() {
//        Training training = new Training();
//        training.setId(1L);
//        doNothing().when(trainingRepo).update(training);
//
//        trainingService.update(training);
//
//        verify(trainingRepo).update(training);
//    }
//
//    @Test
//    void delete() {
//        long id = 1L;
//        doNothing().when(trainingRepo).delete(id);
//
//        trainingService.delete(id);
//
//        verify(trainingRepo).delete(id);
//    }
//
//    @Test
//    void select() {
//        long id = 1L;
//        Training expectedTraining = new Training();
//        when(trainingRepo.select(id)).thenReturn(expectedTraining);
//
//        Training actualTraining = trainingService.select(id);
//
//        assertEquals(expectedTraining, actualTraining);
//        verify(trainingRepo).select(id);
//    }
//
//    @Test
//    void findAll() {
//        List<Training> expectedTrainings = Arrays.asList(new Training(), new Training());
//        when(trainingRepo.findAll()).thenReturn(expectedTrainings);
//
//        List<Training> actualTrainings = trainingService.findAll();
//
//        assertEquals(expectedTrainings, actualTrainings);
//        verify(trainingRepo).findAll();
//    }
//}

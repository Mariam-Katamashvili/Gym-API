//package com.mariamkatamashvlii.gym.serviceImplTests;
//
//import com.mariamkatamashvlii.gym.daoImpl.TraineeDaoImpl;
//import com.mariamkatamashvlii.gym.model.Trainee;
//import com.mariamkatamashvlii.gym.serviceImpl.TraineeServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class TraineeServiceImplTest {
//    @Mock
//    private TraineeDaoImpl traineeDaoImpl;
//
//    @InjectMocks
//    private TraineeServiceImpl traineeService;
//
//    private Trainee trainee;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        trainee = new Trainee("John", "Doe", "John.Doe", "password12", true,
//                LocalDate.of(1990, 1, 1), "123 Main St", 1L);
//    }
//
//    @Test
//    void testCreateTrainee() {
//        traineeService.create(trainee);
//        verify(traineeDaoImpl, times(1)).create(trainee);
//    }
//
//    @Test
//    void testUpdateTrainee() {
//        traineeService.update(trainee.getUserId(), trainee);
//        verify(traineeDaoImpl, times(1)).update(trainee.getUserId(), trainee);
//    }
//
//    @Test
//    void testDeleteTrainee() {
//        traineeService.delete(trainee.getUserId());
//        verify(traineeDaoImpl, times(1)).delete(trainee.getUserId());
//    }
//
//    @Test
//    void testSelectTrainee() {
//        when(traineeDaoImpl.select(trainee.getUserId())).thenReturn(trainee);
//        Trainee result = traineeService.select(trainee.getUserId());
//        assertEquals(trainee, result);
//        verify(traineeDaoImpl, times(1)).select(trainee.getUserId());
//    }
//
//}

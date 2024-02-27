//package com.mariamkatamashvlii.gym.serviceImplTests;
//
//import com.mariamkatamashvlii.gym.daoImpl.TrainerDaoImpl;
//import com.mariamkatamashvlii.gym.model.Trainer;
//import com.mariamkatamashvlii.gym.serviceImpl.TrainerServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class TrainerServiceImplTest {
//    @Mock
//    private TrainerDaoImpl trainerDaoImpl;
//
//    @InjectMocks
//    private TrainerServiceImpl trainerService;
//
//    private Trainer trainer;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        trainer = new Trainer("John", "Doe", "John.Doe", "password12", true, "Specialization", 1L);
//    }
//
//    @Test
//    void testCreateTrainer() {
//        trainerService.create(trainer);
//        verify(trainerDaoImpl, times(1)).create(trainer);
//    }
//
//    @Test
//    void testUpdateTrainer() {
//        trainerService.update(trainer.getUserId(), trainer);
//        verify(trainerDaoImpl, times(1)).update(trainer.getUserId(), trainer);
//    }
//
//    @Test
//    void testSelectTrainer() {
//        when(trainerDaoImpl.select(trainer.getUserId())).thenReturn(trainer);
//        Trainer result = trainerService.select(trainer.getUserId());
//        assertEquals(trainer, result);
//        verify(trainerDaoImpl, times(1)).select(trainer.getUserId());
//    }
//
//}

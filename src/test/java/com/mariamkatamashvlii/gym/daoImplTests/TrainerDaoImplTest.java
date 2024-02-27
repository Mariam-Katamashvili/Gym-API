//package com.mariamkatamashvlii.gym.daoImplTests;
//
//import com.mariamkatamashvlii.gym.daoImpl.TrainerDaoImpl;
//import com.mariamkatamashvlii.gym.model.Trainer;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TrainerDaoImplTest {
//    private TrainerDaoImpl trainerDaoImpl;
//    private Map<Long, Trainer> trainerMap;
//    private Trainer trainer;
//
//    @BeforeEach
//    void setUp() {
//        trainerMap = new HashMap<>();
//        trainerDaoImpl = new TrainerDaoImpl();
//        trainerDaoImpl.setTrainerMap(trainerMap);
//        trainer = new Trainer("John", "Doe", "John.Doe", "1234567890", true, "Specialization", 1L);
//    }
//
//    @Test
//    void testCreateNewTrainer() {
//        trainerDaoImpl.create(trainer);
//        assertTrue(trainerMap.containsKey(trainer.getUserId()));
//        assertEquals(trainer, trainerMap.get(trainer.getUserId()));
//    }
//
//    @Test
//    void testCreateExistingTrainer() {
//        trainerMap.put(trainer.getUserId(), trainer);
//        Trainer newTrainer = new Trainer("Jane", "Doe", "Jane.Doe", "0987654321", true, "Specialization2", 1L);
//        trainerDaoImpl.create(newTrainer);
//        assertEquals(trainer, trainerMap.get(trainer.getUserId()));
//    }
//
//    @Test
//    void testUpdateExistingTrainer() {
//        trainerMap.put(trainer.getUserId(), trainer);
//        Trainer updatedTrainer = new Trainer("John", "Doeee", "John.Doe", "passwordNew1", true, "Specialization Updated", 1L);
//        trainerDaoImpl.update(trainer.getUserId(), updatedTrainer);
//        assertEquals(updatedTrainer, trainerMap.get(trainer.getUserId()));
//    }
//
//    @Test
//    void testUpdateNonExistingTrainerCreatesNew() {
//        Trainer newTrainer = new Trainer("Jane", "Doe", "Jane.Doe", "0987654321", true, "Specialization2", 2L);
//        trainerDaoImpl.update(newTrainer.getUserId(), newTrainer);
//        assertTrue(trainerMap.containsKey(newTrainer.getUserId()));
//        assertEquals(newTrainer, trainerMap.get(newTrainer.getUserId()));
//    }
//
//    @Test
//    void testSelectExistingTrainer() {
//        trainerMap.put(trainer.getUserId(), trainer);
//        Trainer foundTrainer = trainerDaoImpl.select(trainer.getUserId());
//        assertEquals(trainer, foundTrainer);
//    }
//
//    @Test
//    void testSelectNonExistingTrainer() {
//        Trainer foundTrainer = trainerDaoImpl.select(2L);
//        assertNull(foundTrainer);
//    }
//
//}

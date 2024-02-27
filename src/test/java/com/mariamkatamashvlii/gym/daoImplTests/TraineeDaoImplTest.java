//package com.mariamkatamashvlii.gym.daoImplTests;
//
//import com.mariamkatamashvlii.gym.daoImpl.TraineeDaoImpl;
//import com.mariamkatamashvlii.gym.model.Trainee;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TraineeDaoImplTest {
//    private TraineeDaoImpl traineeDaoImpl;
//    private Map<Long, Trainee> traineeMap;
//    private Trainee trainee;
//
//    @BeforeEach
//    void setUp() {
//        traineeMap = new HashMap<>();
//        traineeDaoImpl = new TraineeDaoImpl();
//        traineeDaoImpl.setTraineeMap(traineeMap);
//
//        trainee = new Trainee("John", "Doe", "John.Doe", "pass123123", true,
//                LocalDate.of(2000, 1, 1), "123 Main St", 1L);
//    }
//
//    @Test
//    void testCreateNewTrainee() {
//        traineeDaoImpl.create(trainee);
//        assertTrue(traineeMap.containsKey(trainee.getUserId()));
//        assertEquals(trainee, traineeMap.get(trainee.getUserId()));
//    }
//
//    @Test
//    void testCreateExistingTrainee() {
//        traineeMap.put(trainee.getUserId(), trainee);
//        Trainee newTrainee = new Trainee("Jane", "Doe", "Jane.Doe", "pass456456", true,
//                LocalDate.of(2000, 2, 2), "456 Main St", 1L);
//        traineeDaoImpl.create(newTrainee);
//        assertEquals(trainee, traineeMap.get(trainee.getUserId()));
//    }
//
//
//    @Test
//    void testUpdateExistingTrainee() {
//        traineeMap.put(trainee.getUserId(), trainee);
//        Trainee updatedTrainee = new Trainee("John", "Doeee", "John.Doe", "pass789789", true,
//                LocalDate.of(2000, 1, 1), "123 Main St Updated", 1L);
//        traineeDaoImpl.update(trainee.getUserId(), updatedTrainee);
//        assertEquals(updatedTrainee, traineeMap.get(trainee.getUserId()));
//    }
//
//    @Test
//    void testUpdateNonExistingTraineeCreatesNew() {
//        Trainee newTrainee = new Trainee("Jane", "Doe", "Jane.Doe", "pass456456", true,
//                LocalDate.of(2000, 2, 2), "456 Main St", 2L);
//        traineeDaoImpl.update(newTrainee.getUserId(), newTrainee);
//        assertTrue(traineeMap.containsKey(newTrainee.getUserId()));
//        assertEquals(newTrainee, traineeMap.get(newTrainee.getUserId()));
//    }
//
//
//    @Test
//    void testDeleteExistingTrainee() {
//        traineeMap.put(trainee.getUserId(), trainee);
//        traineeDaoImpl.delete(trainee.getUserId());
//        assertFalse(traineeMap.containsKey(trainee.getUserId()));
//    }
//
//    @Test
//    void testDeleteNonExistingTrainee() {
//        assertFalse(traineeMap.containsKey(2L));
//        traineeDaoImpl.delete(2L);
//        assertFalse(traineeMap.containsKey(2L));
//    }
//
//    @Test
//    void testSelectExistingTrainee() {
//        traineeMap.put(trainee.getUserId(), trainee);
//        Trainee foundTrainee = traineeDaoImpl.select(trainee.getUserId());
//        assertEquals(trainee, foundTrainee);
//    }
//
//    @Test
//    void testSelectNonExistingTrainee() {
//        Trainee foundTrainee = traineeDaoImpl.select(5L);
//        assertNull(foundTrainee);
//    }
//
//}

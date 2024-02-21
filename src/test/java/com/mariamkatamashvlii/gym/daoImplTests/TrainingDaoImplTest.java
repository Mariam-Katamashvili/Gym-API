package com.mariamkatamashvlii.gym.daoImplTests;

import com.mariamkatamashvlii.gym.daoImpl.TrainingDaoImpl;
import com.mariamkatamashvlii.gym.model.Training;
import com.mariamkatamashvlii.gym.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TrainingDaoImplTest {
    private TrainingDaoImpl trainingDaoImpl;
    private Map<String, Training> trainingMap;
    private Training training;

    @BeforeEach
    void setUp() {
        trainingMap = new HashMap<>();
        trainingDaoImpl = new TrainingDaoImpl();
        trainingDaoImpl.setTrainingMap(trainingMap);

        TrainingType trainingType = new TrainingType("Yoga");

        training = new Training(1L, 2L, "YogaSession", trainingType, LocalDate.of(2022, 1, 1), 1.5f);
    }

    @Test
    void testCreateNewTraining() {
        trainingDaoImpl.create(training);
        assertTrue(trainingMap.containsKey(training.getTrainingName()));
        assertEquals(training, trainingMap.get(training.getTrainingName()));
    }

    @Test
    void testCreateExistingTraining() {
        trainingMap.put(training.getTrainingName(), training);
        Training newTraining = new Training(3L, 4L, "YogaSession", new TrainingType("Pilates"), LocalDate.of(2022, 2, 2), 2.0f); // Same training name
        trainingDaoImpl.create(newTraining);
        assertEquals(training, trainingMap.get(training.getTrainingName()));
    }

    @Test
    void testSelectExistingTraining() {
        trainingMap.put(training.getTrainingName(), training);
        Training foundTraining = trainingDaoImpl.select(training.getTrainingName());
        assertEquals(training, foundTraining);
    }

    @Test
    void testSelectNonExistingTraining() {
        Training foundTraining = trainingDaoImpl.select("NonExistentSession");
        assertNull(foundTraining);
    }


}

package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.entity.Training;

import java.sql.Date;
import java.util.List;

public interface TrainingService {
    Training create(String traineeUsername, String trainerUsername, String trainingName, Date date, Number duration);

    Training update(Training training);

    void delete(long id);

    Training select(long id);

    List<Training> findAll();
}

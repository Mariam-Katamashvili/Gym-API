package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.entity.Training;

import java.sql.Date;

public interface TrainingService {
    Training create(String traineeUsername, String trainerUsername, String trainingName, Date date, Number duration);

    Training select(Long id);

}

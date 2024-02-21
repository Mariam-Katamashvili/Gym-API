package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.model.Training;

public interface TrainingService {
    void create(Training training);

    Training select(String trainingName);

}

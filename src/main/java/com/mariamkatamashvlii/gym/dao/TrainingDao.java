package com.mariamkatamashvlii.gym.dao;

import com.mariamkatamashvlii.gym.model.Training;

public interface TrainingDao {

    void create (Training training);
    Training select (String trainingName);

}

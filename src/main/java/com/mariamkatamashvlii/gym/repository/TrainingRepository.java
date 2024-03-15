package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.Training;

public interface TrainingRepository {
    Training create(Training training);

    void delete(long id);

    Training select(long id);

}

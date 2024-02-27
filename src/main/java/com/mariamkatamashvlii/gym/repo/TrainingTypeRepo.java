package com.mariamkatamashvlii.gym.repo;

import com.mariamkatamashvlii.gym.model.TrainingType;

import java.util.List;

public interface TrainingTypeRepo {
    void create(TrainingType trainingType);

    void update(TrainingType trainingType);

    void delete(long id);

    TrainingType select(long id);

    List<TrainingType> findAll();
}

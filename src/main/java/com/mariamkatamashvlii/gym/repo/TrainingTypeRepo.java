package com.mariamkatamashvlii.gym.repo;

import com.mariamkatamashvlii.gym.model.TrainingType;

import java.util.List;

public interface TrainingTypeRepo {
    TrainingType select(long id);

    List<TrainingType> findAll();
}

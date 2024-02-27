package com.mariamkatamashvlii.gym.dao;

import com.mariamkatamashvlii.gym.model.TrainingType;

import java.util.List;

public interface TrainingTypeDao {
    void create(TrainingType trainingType);
    void update(TrainingType trainingType);
    void delete(long trainingTypeId);
    TrainingType select(long trainingTypeId);
    List<TrainingType> findAll();
}

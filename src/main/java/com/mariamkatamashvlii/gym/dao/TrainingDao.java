package com.mariamkatamashvlii.gym.dao;

import com.mariamkatamashvlii.gym.model.Training;

import java.util.List;

public interface TrainingDao {
    void create (Training training);
    void update (Training training);
    void delete (long trainingId);
    Training select (long trainingId);
    List<Training> findAll();

}

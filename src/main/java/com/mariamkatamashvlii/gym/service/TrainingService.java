package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.entity.Training;

import java.util.List;

public interface TrainingService {
    void create(Training training);

    void update(Training training);

    void delete(long id);

    Training select(long id);

    List<Training> findAll();
}

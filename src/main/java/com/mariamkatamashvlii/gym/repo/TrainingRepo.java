package com.mariamkatamashvlii.gym.repo;

import com.mariamkatamashvlii.gym.model.Training;

import java.util.List;

public interface TrainingRepo {
    void create(Training training);

    void update(Training training);

    void delete(long id);

    Training select(long id);

    List<Training> findAll();
}

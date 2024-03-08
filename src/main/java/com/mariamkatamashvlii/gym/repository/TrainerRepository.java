package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.Trainer;

import java.util.List;

public interface TrainerRepository {
    void create(Trainer trainer);

    void update(Trainer trainer);

    void delete(long id);

    Trainer select(long id);

    Trainer select(String username);

    List<Trainer> findAll();
}

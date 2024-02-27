package com.mariamkatamashvlii.gym.repo;

import com.mariamkatamashvlii.gym.model.Trainer;

import java.util.List;

public interface TrainerRepo {
    void create(Trainer trainer);

    void update(Trainer trainer);

    void delete(long id);

    Trainer select(long id);

    Trainer select(String username);

    List<Trainer> findAll();
}

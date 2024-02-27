package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.model.Trainer;

import java.util.List;

public interface TrainerService {
    void create(Trainer trainer);
    void update(Trainer trainer);
    void delete(long id);
    Trainer select(long id);
    List<Trainer> findAll();
}

package com.mariamkatamashvlii.gym.dao;

import com.mariamkatamashvlii.gym.model.Trainer;

import java.util.List;

public interface TrainerDao {
    void create (Trainer trainer);
    void update (Trainer trainer);
    void delete (long trainerId);
    Trainer select (long trainerId);
    List<Trainer> findAll();

}

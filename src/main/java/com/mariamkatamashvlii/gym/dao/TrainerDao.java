package com.mariamkatamashvlii.gym.dao;

import com.mariamkatamashvlii.gym.model.Trainer;

public interface TrainerDao {
    void create (Trainer trainer);
    void update (long trainerId, Trainer trainer);
    Trainer select (long trainerId);

}

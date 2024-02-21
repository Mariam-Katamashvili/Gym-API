package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.model.Trainer;

public interface TrainerService {
    void create(Trainer trainer);
    void update(long trainerId, Trainer trainer);
    Trainer select(long trainerId);

}

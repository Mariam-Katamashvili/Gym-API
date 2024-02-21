package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.model.Trainee;

public interface TraineeService {
    void create(Trainee trainee);

    void update(long traineeId, Trainee trainee);

    void delete (long traineeId);

    Trainee select(long traineeId);

}
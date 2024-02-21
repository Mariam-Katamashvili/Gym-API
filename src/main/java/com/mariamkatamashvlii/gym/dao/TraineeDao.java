package com.mariamkatamashvlii.gym.dao;

import com.mariamkatamashvlii.gym.model.Trainee;

public interface TraineeDao {

    void create(Trainee trainee);
    void update(long traineeId, Trainee trainee);
    void delete(long traineeId);
    Trainee select(long traineeId);

}

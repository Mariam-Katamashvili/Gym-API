package com.mariamkatamashvlii.gym.dao;

import com.mariamkatamashvlii.gym.model.Trainee;

import java.util.List;

public interface TraineeDao {
    void create(Trainee trainee);
    void update(Trainee trainee);
    void delete(long traineeId);
    Trainee select(long traineeId);
    List<Trainee> findAll();

}

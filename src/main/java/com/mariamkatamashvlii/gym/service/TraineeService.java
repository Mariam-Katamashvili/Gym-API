package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.model.Trainee;

import java.util.List;

public interface TraineeService {
    void create(Trainee trainee);
    void update(Trainee trainee);
    void delete(long id);
    Trainee select(long id);
    List<Trainee> findAll();
}
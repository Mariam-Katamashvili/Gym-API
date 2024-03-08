package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.Trainee;

import java.util.List;

public interface TraineeRepository {
    void create(Trainee trainee);

    void update(Trainee trainee);

    void delete(long id);

    void delete(String username);

    Trainee select(long id);

    Trainee select(String username);

    List<Trainee> findAll();
}

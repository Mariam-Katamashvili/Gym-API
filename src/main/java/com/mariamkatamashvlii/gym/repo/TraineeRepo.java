package com.mariamkatamashvlii.gym.repo;

import com.mariamkatamashvlii.gym.model.Trainee;

import java.util.List;

public interface TraineeRepo {
    void create(Trainee trainee);

    void update(Trainee trainee);

    void delete(long id);

    void delete(String username);

    Trainee select(long id);

    Trainee select(String username);

    List<Trainee> findAll();
}

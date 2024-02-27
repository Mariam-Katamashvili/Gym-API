package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.model.Trainee;

import java.util.List;

public interface TraineeService {
    void create(Trainee trainee);

    void update(Trainee trainee);

    void delete(long id);

    void delete(String username);

    Trainee select(long id);

    Trainee select(String username);

    boolean checkCredentials(String username, String password);

    boolean changePassword(String username, String currPassword, String newPassword);

    void toggleActivation(String username, boolean isActive);

    List<Trainee> findAll();

}
package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.model.Trainer;
import jakarta.persistence.Table;

import java.util.List;

public interface TrainerService {
    void create(Trainer trainer);

    void update(Trainer trainer);

    void delete(long id);

    Trainer select(long id);

    Trainer select(String username);

    boolean checkCredentials(String username, String password);

    boolean changePassword(String username, String currPassword, String newPassword);

    void toggleActivation(String username, boolean isActive);

    List<Trainer> findAll();
}

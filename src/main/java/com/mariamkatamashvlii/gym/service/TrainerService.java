package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;

import java.sql.Date;
import java.util.List;

public interface TrainerService {
    void create(Trainer trainer);

    void update(Trainer trainer);

    void delete(long id);

    Trainer select(long id);

    Trainer select(String username, String password);

    boolean checkCredentials(String username, String password);

    boolean changePassword(String username, String currPassword, String newPassword);

    void toggleActivation(String username, String password, boolean isActive);

    void createTrainerProfile(long trainingType, long user);

    List<Training> getTrainingList(String username, String password, Date fromDate,
                                   Date toDate, String traineeName);

    List<Trainer> findAll();
}

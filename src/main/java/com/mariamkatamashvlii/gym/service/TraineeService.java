package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.model.Trainee;
import com.mariamkatamashvlii.gym.model.Trainer;
import com.mariamkatamashvlii.gym.model.Training;
import com.mariamkatamashvlii.gym.model.TrainingType;

import java.sql.Date;
import java.util.List;

public interface TraineeService {
    void create(Trainee trainee);

    void update(Trainee trainee);

    void delete(long id);

    void delete(String username, String password);

    Trainee select(long id);

    Trainee select(String username, String password);

    boolean checkCredentials(String username, String password);

    boolean changePassword(String username, String currPassword, String newPassword);

    void toggleActivation(String username, String password, boolean isActive);

    void updateTrainerList(String username, String password, List<Trainer> trainerList);

    void createTraineeProfile(Date dob, String address, long userId);

    List<Training> geTrainingList(String username, String password, Date fromDate,
                                  Date toDate, String trainerName, TrainingType trainingType);

    List<Trainer> getNotAssignedTrainerList(String username, String password);

    List<Trainee> findAll();
}
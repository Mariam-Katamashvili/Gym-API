package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.RegistrationDTO;
import com.mariamkatamashvlii.gym.dto.TraineeProfileDTO;
import com.mariamkatamashvlii.gym.dto.TrainerDTO;
import com.mariamkatamashvlii.gym.dto.TrainerUsenameDTO;
import com.mariamkatamashvlii.gym.dto.TrainingDTO;
import com.mariamkatamashvlii.gym.dto.UpdateTraineeDTO;
import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.TrainingType;

import java.sql.Date;
import java.util.List;

public interface TraineeService {
    Trainee create(Trainee trainee);

    Trainee update(Trainee trainee);

    void delete(String username);

    Trainee select(String username);

    boolean changePassword(String username, String currPassword, String newPassword);

    void activateTrainee (String username, Boolean isActive);

    void deactivateTrainee (String username, Boolean isActive);

    List<TrainerDTO> updateTrainers(String username, List<TrainerUsenameDTO> trainers);

    Trainee createTraineeProfile(Date dob, String address, Long userId);

    List<TrainingDTO> getTrainings(String username, Date fromDate,
                                   Date toDate, String trainerName, TrainingType trainingType);

    List<TrainerDTO> getNotAssignedTrainers(String username);

    RegistrationDTO registerTrainee (String firstName, String lastName, Date birthday, String address);

    TraineeProfileDTO traineeProfile(String username);

    UpdateTraineeDTO updateProfile(String username, String firstName, String lastName, Date birthday, String address, Boolean isActive);

    List<Trainee> findAll();
}
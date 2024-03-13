package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.RegistrationDTO;
import com.mariamkatamashvlii.gym.dto.TrainerProfileDTO;
import com.mariamkatamashvlii.gym.dto.TrainingDTO;
import com.mariamkatamashvlii.gym.dto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.dto.UpdateTrainerDTO;
import com.mariamkatamashvlii.gym.entity.Trainer;

import java.sql.Date;
import java.util.List;

public interface TrainerService {
    Trainer create(Trainer trainer);

    Trainer update(Trainer trainer);

    Trainer select(String username);

    boolean changePassword(String username, String currPassword, String newPassword);

    void activateTrainer (String username, boolean isActive);

    void deactivateTrainer (String username, boolean isActive);

    Trainer createTrainerProfile(long trainingTypeId, long userId);

    List<TrainingDTO> getTrainings(String username, Date fromDate,
                                   Date toDate, String traineeName);

    RegistrationDTO registerTrainer(String firstName, String lastName, Long trainingTypeId);
    TrainerProfileDTO trainerProfile(String username);
    UpdateTrainerDTO updateProfile(String username, String firstName, String lastName, TrainingTypeDTO specialization, boolean isActive);
    List<Trainer> findAll();
}

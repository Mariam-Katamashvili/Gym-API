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

    void activateTrainer (String username, Boolean isActive);

    void deactivateTrainer (String username, Boolean isActive);

    Trainer createTrainerProfile(Long trainingTypeId, Long userId);

    List<TrainingDTO> getTrainings(String username, Date fromDate,
                                   Date toDate, String traineeName);

    RegistrationDTO registerTrainer(String firstName, String lastName, Long trainingTypeId);
    TrainerProfileDTO trainerProfile(String username);
    UpdateTrainerDTO updateProfile(String username, String firstName, String lastName, TrainingTypeDTO specialization, Boolean isActive);
    List<Trainer> findAll();
}

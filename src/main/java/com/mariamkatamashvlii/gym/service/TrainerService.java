package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.TrainerProfileDTO;
import com.mariamkatamashvlii.gym.dto.TrainingDTO;
import com.mariamkatamashvlii.gym.dto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.dto.UpdateTrainerDTO;

import java.sql.Date;
import java.util.List;

public interface TrainerService {

    RegistrationResponseDTO registerTrainer(String firstName, String lastName, Long trainingTypeId);

    TrainerProfileDTO trainerProfile(String username);

    UpdateTrainerDTO updateProfile(String username, String firstName, String lastName, TrainingTypeDTO specialization, Boolean isActive);

    List<TrainingDTO> getTrainings(String username, Date fromDate, Date toDate, String traineeName);

    void activateTrainer(String username, Boolean isActive);

    void deactivateTrainer(String username, Boolean isActive);

}

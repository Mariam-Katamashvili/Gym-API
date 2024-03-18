package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.TraineeProfileDTO;
import com.mariamkatamashvlii.gym.dto.TrainerDTO;
import com.mariamkatamashvlii.gym.dto.TrainerUsenameDTO;
import com.mariamkatamashvlii.gym.dto.TrainingDTO;
import com.mariamkatamashvlii.gym.dto.UpdateTraineeDTO;
import com.mariamkatamashvlii.gym.entity.TrainingType;

import java.sql.Date;
import java.util.List;

public interface TraineeService {
    RegistrationResponseDTO registerTrainee(TraineeProfileDTO traineeProfileDTO);
    TraineeProfileDTO getTraineeProfile(String username);

    UpdateTraineeDTO updateProfile(String username, String firstName, String lastName, Date birthday, String address, Boolean isActive);

    void delete(String username);

    List<TrainerDTO> getNotAssignedTrainers(String username);

    List<TrainingDTO> getTrainings(String username, Date fromDate, Date toDate, String trainerName, TrainingType trainingType);

    List<TrainerDTO> updateTrainers(String username, List<TrainerUsenameDTO> trainers);

    void activateTrainee(String username, Boolean isActive);

    void deactivateTrainee(String username, Boolean isActive);

}
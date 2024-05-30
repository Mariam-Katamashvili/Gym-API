package com.mariamkatamashvili.gym.service;

import com.mariamkatamashvili.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvili.gym.dto.traineeDto.ProfileResponseDTO;
import com.mariamkatamashvili.gym.dto.traineeDto.RegistrationRequestDTO;
import com.mariamkatamashvili.gym.dto.traineeDto.UpdateRequestDTO;
import com.mariamkatamashvili.gym.dto.traineeDto.UpdateResponseDTO;
import com.mariamkatamashvili.gym.dto.traineeDto.UpdateTrainersRequestDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.TrainerDTO;
import com.mariamkatamashvili.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvili.gym.dto.trainingDto.TrainingsRequestDTO;
import com.mariamkatamashvili.gym.dto.ToggleActivationDTO;

import java.util.List;

public interface TraineeService {
    RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO);

    ProfileResponseDTO getProfile(String username);

    UpdateResponseDTO updateProfile(UpdateRequestDTO updateRequestDTO);

    void delete(String username);

    List<TrainerDTO> getUnassignedTrainers(String username);

    List<TrainingResponseDTO> getTrainings(TrainingsRequestDTO trainingsRequestDTO);

    List<TrainerDTO> updateTrainers(UpdateTrainersRequestDTO updateTrainersRequestDTO);

    void toggleActivation(ToggleActivationDTO toggleActivationDTO);
}
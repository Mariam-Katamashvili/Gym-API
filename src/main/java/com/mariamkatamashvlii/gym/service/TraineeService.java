package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateTrainersRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingsRequestDTO;

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
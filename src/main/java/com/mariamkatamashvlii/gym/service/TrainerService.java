package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingsRequestDTO;

import java.util.List;

public interface TrainerService {
    RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO);

    ProfileResponseDTO getProfile(String username);

    UpdateResponseDTO updateProfile(UpdateRequestDTO updateRequestDTO);

    List<TrainingResponseDTO> getTrainings(TrainingsRequestDTO trainingsRequestDTO);

    void toggleActivation(ToggleActivationDTO toggleActivationDTO);
}
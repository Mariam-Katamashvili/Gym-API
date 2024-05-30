package com.mariamkatamashvili.gym.service;

import com.mariamkatamashvili.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.ProfileResponseDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.RegistrationRequestDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.UpdateRequestDTO;
import com.mariamkatamashvili.gym.dto.trainerDto.UpdateResponseDTO;
import com.mariamkatamashvili.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvili.gym.dto.trainingDto.TrainingsRequestDTO;
import com.mariamkatamashvili.gym.dto.ToggleActivationDTO;

import java.util.List;

public interface TrainerService {
    RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO);

    ProfileResponseDTO getProfile(String username);

    UpdateResponseDTO updateProfile(UpdateRequestDTO updateRequestDTO);

    List<TrainingResponseDTO> getTrainings(TrainingsRequestDTO trainingsRequestDTO);

    void toggleActivation(ToggleActivationDTO toggleActivationDTO);
}
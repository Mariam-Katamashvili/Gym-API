package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingsRequestDTO;
import com.mariamkatamashvlii.gym.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trainers")
public class TrainerController {
    private final TrainerService trainerService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDTO> registration(
            @RequestBody RegistrationRequestDTO registrationRequest) {
        RegistrationResponseDTO registration = trainerService.register(registrationRequest);
        return ResponseEntity.ok(registration);
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<ProfileResponseDTO> getProfile(
            @PathVariable String username) {
        ProfileResponseDTO response = trainerService.getProfile(username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UpdateResponseDTO> update(
            @RequestBody UpdateRequestDTO updateRequest) {
        UpdateResponseDTO updateResponse = trainerService.updateProfile(updateRequest);
        return ResponseEntity.ok(updateResponse);
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingResponseDTO>> trainerTrainings(
            @RequestBody TrainingsRequestDTO traineeTrainingsRequest) {
        List<TrainingResponseDTO> trainingResponse = trainerService.getTrainings(traineeTrainingsRequest);
        return ResponseEntity.ok(trainingResponse);
    }

    @PatchMapping("/{username}/toggleActivation")
    public ResponseEntity<String> toggleActivation(
            @RequestBody ToggleActivationDTO toggleActivation) {
        trainerService.toggleActivation(toggleActivation);
        return ResponseEntity.ok("Activation status changed");
    }
}
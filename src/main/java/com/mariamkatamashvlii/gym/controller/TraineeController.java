package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.dto.ToggleActivationDTO;
import com.mariamkatamashvlii.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.ProfileResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.RegistrationRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateRequestDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateResponseDTO;
import com.mariamkatamashvlii.gym.dto.traineeDto.UpdateTrainersRequestDTO;
import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingResponseDTO;
import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingsRequestDTO;
import com.mariamkatamashvlii.gym.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/trainees")
public class TraineeController {
    private final TraineeService traineeService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDTO> registration(
            @RequestBody RegistrationRequestDTO registrationRequest) {
        RegistrationResponseDTO registrationResponse = traineeService.register(registrationRequest);
        return ResponseEntity.ok(registrationResponse);
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<ProfileResponseDTO> getProfile(
            @PathVariable String username) {
        ProfileResponseDTO profile = traineeService.getProfile(username);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UpdateResponseDTO> update(
            @RequestBody UpdateRequestDTO updateRequest) {
        UpdateResponseDTO response = traineeService.updateProfile(updateRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> delete(
            @PathVariable String username) {
        traineeService.delete(username);
        return ResponseEntity.ok("Trainee deleted successfully");
    }

    @GetMapping("/{username}/unassigned-trainers")
    public ResponseEntity<List<TrainerDTO>> getUnassigned(
            @PathVariable String username) {
        List<TrainerDTO> trainer = traineeService.getUnassignedTrainers(username);
        return ResponseEntity.ok(trainer);
    }

    @PutMapping("/{username}/trainers/update")
    public ResponseEntity<List<TrainerDTO>> updateTrainers(
            @RequestBody UpdateTrainersRequestDTO updateTrainersRequest) {
        List<TrainerDTO> trainer = traineeService.updateTrainers(updateTrainersRequest);
        return ResponseEntity.ok(trainer);
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingResponseDTO>> traineeTrainings(
            @RequestBody TrainingsRequestDTO traineeTrainingsRequest) {
        List<TrainingResponseDTO> trainingResponse = traineeService.getTrainings(traineeTrainingsRequest);
        return ResponseEntity.ok(trainingResponse);
    }

    @PatchMapping("/{username}/toggleActivation")
    public ResponseEntity<String> toggleActivation(
            @RequestBody ToggleActivationDTO toggleActivation) {
        traineeService.toggleActivation(toggleActivation);
        return ResponseEntity.ok("Activation status changed");
    }
}
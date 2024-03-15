package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.dto.RegistrationDTO;
import com.mariamkatamashvlii.gym.dto.TrainerProfileDTO;
import com.mariamkatamashvlii.gym.dto.TrainingDTO;
import com.mariamkatamashvlii.gym.dto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.dto.UpdateTrainerDTO;
import com.mariamkatamashvlii.gym.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trainers")
public class TrainerController {
    private final TrainerService trainerService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationDTO>trainerRegistration(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam Long specialization){
        RegistrationDTO registrationDTO = trainerService.registerTrainer(firstname, lastname, specialization);
        return ResponseEntity.ok(registrationDTO);
    }

    @GetMapping("/getProfile/{username:.+}")
    public ResponseEntity<TrainerProfileDTO> getTraineeProfile(
            @PathVariable String username) {
        TrainerProfileDTO profile = trainerService.trainerProfile(username);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<UpdateTrainerDTO> updateTrainer(
            @RequestParam String username,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam TrainingTypeDTO specialization,
            @RequestParam Boolean isActive) {
        UpdateTrainerDTO updateTrainerDTO = trainerService.updateProfile(username, firstname, lastname, specialization, isActive);
        return ResponseEntity.ok(updateTrainerDTO);
    }

    @GetMapping("/trainerTrainings")
    public ResponseEntity<List<TrainingDTO>> trainerTrainings (
            @RequestParam String username,
            @RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to,
            @RequestParam(required = false) String traineeName){
        List<TrainingDTO> trainingDTO = trainerService.getTrainings(username, from, to, traineeName);
        return ResponseEntity.ok(trainingDTO);
    }

    @PatchMapping("/activation")
    public ResponseEntity<Void> activateTrainee(
            @RequestParam String username,
            @RequestParam Boolean isActive) {
        trainerService.activateTrainer(username, isActive);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/deactivation")
    public ResponseEntity<Void> deactivateTrainee(
            @RequestParam String username,
            @RequestParam Boolean isActive) {
        trainerService.deactivateTrainer(username, isActive);
        return ResponseEntity.ok().build();
    }
}

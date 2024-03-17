package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.dto.RegistrationDTO;
import com.mariamkatamashvlii.gym.dto.TraineeProfileDTO;
import com.mariamkatamashvlii.gym.dto.TrainerDTO;
import com.mariamkatamashvlii.gym.dto.TrainerUsenameDTO;
import com.mariamkatamashvlii.gym.dto.TrainingDTO;
import com.mariamkatamashvlii.gym.dto.UpdateTraineeDTO;
import com.mariamkatamashvlii.gym.entity.TrainingType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/traineeProfile")
public class TraineeController {
    private final TraineeService traineeService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationDTO> registration(
            @RequestBody TraineeProfileDTO traineeProfileDTO) {
        RegistrationDTO registrationDTO = traineeService.registerTrainee(traineeProfileDTO);
        return ResponseEntity.ok(registrationDTO);
    }

    @GetMapping("/{username}")
    public ResponseEntity<TraineeProfileDTO> getTraineeProfile(
            @PathVariable String username) {
        TraineeProfileDTO profile = traineeService.getTraineeProfile(username);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateTraineeDTO> updateTrainee(
            @RequestParam String username,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam(required = false) Date birthday,
            @RequestParam(required = false) String address,
            @RequestParam Boolean isActive) {
        UpdateTraineeDTO updateTraineeDTO = traineeService.updateProfile(username, firstname, lastname, birthday, address, isActive);
        return ResponseEntity.ok(updateTraineeDTO);
    }

    @DeleteMapping("/{username}/delete")
    public ResponseEntity<Void> deleteTrainee(
            @PathVariable String username) {
        traineeService.delete(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/unassigned")
    public ResponseEntity<List<TrainerDTO>> getNotAssigned(
            @PathVariable String username) {
        List<TrainerDTO> trainerDTO = traineeService.getNotAssignedTrainers(username);
        return ResponseEntity.ok(trainerDTO);
    }

    @GetMapping("/Trainings")
    public ResponseEntity<List<TrainingDTO>> traineeTrainings(
            @RequestParam String username,
            @RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to,
            @RequestParam(required = false) String trainerName,
            @RequestParam(required = false) TrainingType trainingtype) {
        List<TrainingDTO> trainingDTO = traineeService.getTrainings(username, from, to, trainerName, trainingtype);
        return ResponseEntity.ok(trainingDTO);
    }

    @PutMapping("/updateTrainers")
    public ResponseEntity<List<TrainerDTO>> updateTrainers(
            @RequestParam String username,
            @RequestParam List<TrainerUsenameDTO> trainerUsernames) {
        List<TrainerDTO> trainerDTO = traineeService.updateTrainers(username, trainerUsernames);
        return ResponseEntity.ok(trainerDTO);
    }


    @PatchMapping("/activation")
    public ResponseEntity<Void> activateTrainee(
            @RequestParam String username,
            @RequestParam Boolean isActive) {
        traineeService.activateTrainee(username, isActive);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/deactivation")
    public ResponseEntity<Void> deactivateTrainee(
            @RequestParam String username,
            @RequestParam Boolean isActive) {
        traineeService.deactivateTrainee(username, isActive);
        return ResponseEntity.ok().build();
    }
}
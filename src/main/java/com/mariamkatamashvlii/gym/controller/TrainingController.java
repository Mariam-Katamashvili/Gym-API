package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trainings")
public class TrainingController {
    private final TrainingService trainingService;

    @PostMapping("/addTraining")
    public ResponseEntity<Training> addTraining(
            @RequestParam String traineeUsername,
            @RequestParam String trainerUsername,
            @RequestParam String trainingName,
            @RequestParam Date date,
            @RequestParam Number duration
    ) {
        Training addTraining = trainingService.create(traineeUsername, trainerUsername, trainingName, date, duration);
        return ResponseEntity.ok(addTraining);
    }
}

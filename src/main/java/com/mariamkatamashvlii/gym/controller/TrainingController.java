package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.dto.trainingDto.TrainingRequestDTO;
import com.mariamkatamashvlii.gym.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trainings")
public class TrainingController {
    private final TrainingService trainingService;

    @PostMapping
    public ResponseEntity<String> add(
            @RequestBody TrainingRequestDTO trainingRequestDTO) {
        trainingService.create(trainingRequestDTO);
        return ResponseEntity.ok("Training added successfully");
    }
}

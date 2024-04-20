package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvlii.gym.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/training-types")
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;

    @GetMapping
    public List<TrainingTypeDTO> getAll() {
        return trainingTypeService.findAll();
    }
}

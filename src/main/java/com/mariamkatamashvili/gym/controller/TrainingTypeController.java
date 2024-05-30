package com.mariamkatamashvili.gym.controller;

import com.mariamkatamashvili.gym.dto.trainingTypeDto.TrainingTypeDTO;
import com.mariamkatamashvili.gym.service.TrainingTypeService;
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
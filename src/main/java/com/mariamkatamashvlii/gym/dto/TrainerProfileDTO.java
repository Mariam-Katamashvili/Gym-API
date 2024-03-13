package com.mariamkatamashvlii.gym.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class TrainerProfileDTO {
    private String firstName;
    private String lastName;
    private TrainingTypeDTO specialization;
    private boolean isActive;
    private List<TraineeDTO> trainees;

    public TrainerProfileDTO(String firstName, String lastName, TrainingTypeDTO specialization, boolean isActive, List<TraineeDTO> trainees) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
        this.trainees = trainees;
    }
}

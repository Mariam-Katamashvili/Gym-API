package com.mariamkatamashvlii.gym.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UpdateTrainerDTO {
    private String username;
    private String firstName;
    private String lastName;
    private TrainingTypeDTO specialization;
    private boolean isActive;
    private List<TraineeDTO> trainees;

    public UpdateTrainerDTO(String username, String firstName, String lastName, TrainingTypeDTO specialization, boolean isActive, List<TraineeDTO> trainees) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
        this.trainees = trainees;
    }
}

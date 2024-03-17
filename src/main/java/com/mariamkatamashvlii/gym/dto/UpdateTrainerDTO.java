package com.mariamkatamashvlii.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class UpdateTrainerDTO {
    private String username;
    private String firstName;
    private String lastName;
    private TrainingTypeDTO specialization;
    private Boolean isActive;
    private List<TraineeDTO> trainees;
}

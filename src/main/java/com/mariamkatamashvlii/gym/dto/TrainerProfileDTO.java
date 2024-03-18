package com.mariamkatamashvlii.gym.dto;

import com.mariamkatamashvlii.gym.dto.trainingTypeDto.TrainingTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class TrainerProfileDTO {
    private String firstName;
    private String lastName;
    private TrainingTypeDTO specialization;
    private Boolean isActive;
    private List<TraineeDTO> trainees;
}

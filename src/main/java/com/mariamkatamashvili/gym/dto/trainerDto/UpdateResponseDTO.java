package com.mariamkatamashvili.gym.dto.trainerDto;

import com.mariamkatamashvili.gym.dto.traineeDto.TraineeDTO;
import com.mariamkatamashvili.gym.dto.trainingTypeDto.TrainingTypeDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResponseDTO {
    @Valid
    private String username;

    @Valid
    private String firstName;

    @Valid
    private String lastName;

    @Valid
    private TrainingTypeDTO specialization;

    @Valid
    private Boolean isActive;

    @Valid
    private List<TraineeDTO> trainees;

}
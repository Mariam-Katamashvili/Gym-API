package com.mariamkatamashvili.gym.dto.trainerDto;

import com.mariamkatamashvili.gym.dto.trainingTypeDto.TrainingTypeDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDTO {
    @Valid
    private String username;

    @Valid
    private String firstName;

    @Valid
    private String lastName;

    @Valid
    private TrainingTypeDTO specialization;
}
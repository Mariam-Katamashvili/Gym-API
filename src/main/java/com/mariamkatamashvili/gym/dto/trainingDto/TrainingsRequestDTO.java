package com.mariamkatamashvili.gym.dto.trainingDto;

import com.mariamkatamashvili.gym.dto.trainingTypeDto.TrainingTypeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingsRequestDTO {
    @Valid
    @NotNull
    private String username;

    @Valid
    private LocalDate startDate;

    @Valid
    private LocalDate endDate;

    @Valid
    private String name;

    @Valid
    private TrainingTypeDTO trainingType;
}
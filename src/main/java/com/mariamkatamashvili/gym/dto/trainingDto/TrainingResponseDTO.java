package com.mariamkatamashvili.gym.dto.trainingDto;

import com.mariamkatamashvili.gym.dto.trainingTypeDto.TrainingTypeDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingResponseDTO {
    @Valid
    private String trainingName;

    @Valid
    private LocalDate date;

    @Valid
    private TrainingTypeDTO trainingType;

    @Valid
    private Integer duration;

    @Valid
    private String name;
}
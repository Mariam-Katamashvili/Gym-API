package com.mariamkatamashvili.gym.dto.trainingDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequestDTO {
    @Valid
    @NotNull
    private String traineeUsername;

    @Valid
    @NotNull
    private String trainerUsername;

    @Valid
    @NotNull
    private String trainingName;

    @Valid
    @NotNull
    private LocalDate date;

    @Valid
    @NotNull
    private Integer duration;
}
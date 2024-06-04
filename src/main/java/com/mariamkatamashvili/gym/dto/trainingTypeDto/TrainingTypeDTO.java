package com.mariamkatamashvili.gym.dto.trainingTypeDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingTypeDTO {
    @Valid
    private Long trainingTypeId;

    @Valid
    private String trainingTypeName;
}
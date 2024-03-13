package com.mariamkatamashvlii.gym.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TrainingTypeDTO {
    private Long trainingTypeId;
    private String trainingTypeName;

    public TrainingTypeDTO(Long trainingTypeId, String trainingTypeName) {
        this.trainingTypeId = trainingTypeId;
        this.trainingTypeName = trainingTypeName;
    }
}

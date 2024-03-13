package com.mariamkatamashvlii.gym.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TrainerDTO {
    private String username;
    private String firstName;
    private String lastName;
    private TrainingTypeDTO specialization;

    public TrainerDTO(String username) {
        this.username = username;
    }
}

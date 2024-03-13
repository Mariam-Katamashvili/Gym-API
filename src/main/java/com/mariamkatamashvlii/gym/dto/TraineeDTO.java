package com.mariamkatamashvlii.gym.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor
public class TraineeDTO {
    private String username;
    private String firstName;
    private String lastName;


    public TraineeDTO(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

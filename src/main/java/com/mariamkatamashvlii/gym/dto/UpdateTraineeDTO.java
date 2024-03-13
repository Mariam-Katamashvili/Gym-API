package com.mariamkatamashvlii.gym.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UpdateTraineeDTO {
    private String username;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String address;
    private boolean isActive;
    private List<TrainerDTO> trainers;

    public UpdateTraineeDTO(String username, String firstName, String lastName, Date birthday, String address, boolean isActive, List<TrainerDTO> trainers) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.isActive = isActive;
        this.trainers = trainers;
    }
}

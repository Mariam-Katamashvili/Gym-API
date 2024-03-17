package com.mariamkatamashvlii.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class UpdateTraineeDTO {
    private String username;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String address;
    private Boolean isActive;
    private List<TrainerDTO> trainers;
}

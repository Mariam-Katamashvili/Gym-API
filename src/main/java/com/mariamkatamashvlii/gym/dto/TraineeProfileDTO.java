package com.mariamkatamashvlii.gym.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
public class TraineeProfileDTO {
    @NotNull
    @Valid
    private String firstName;

    @NotNull
    @Valid
    private String lastName;

    @Valid
    private Date birthday;

    @Valid
    private String address;

    @Valid
    private Boolean isActive;

    @Valid
    private List<TrainerDTO> trainers;
}

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
public class UpdateTraineeDTO {
    @Valid
    @NotNull
    private String username;

    @Valid
    @NotNull
    private String firstName;

    @Valid
    @NotNull
    private String lastName;

    @Valid
    private Date birthday;

    @Valid
    private String address;

    @Valid
    @NotNull
    private Boolean isActive;

    @Valid
    private List<TrainerDTO> trainers;
}

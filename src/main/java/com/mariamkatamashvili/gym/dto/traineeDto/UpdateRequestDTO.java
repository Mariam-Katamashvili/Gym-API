package com.mariamkatamashvili.gym.dto.traineeDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequestDTO {
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
    private LocalDate birthday;

    @Valid
    private String address;

    @Valid
    @NotNull
    private Boolean isActive;
}
package com.mariamkatamashvlii.gym.dto.traineeDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class RegistrationRequestDTO {
    @NotNull
    @Valid
    private String firstName;

    @NotNull
    @Valid
    private String lastName;

    @Valid
    private LocalDate birthday;

    @Valid
    private String address;
}

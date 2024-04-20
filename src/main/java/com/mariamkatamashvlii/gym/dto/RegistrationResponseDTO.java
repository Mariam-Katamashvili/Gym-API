package com.mariamkatamashvlii.gym.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO {
    @Valid
    private String username;

    @Valid
    private String password;

    @Valid
    private String token;
}
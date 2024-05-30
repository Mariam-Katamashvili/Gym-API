package com.mariamkatamashvili.gym.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToggleActivationDTO {
    @Valid
    @NotNull
    private String username;

    @Valid
    @NotNull
    private Boolean isActive;
}
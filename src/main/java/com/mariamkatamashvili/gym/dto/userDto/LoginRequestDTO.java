package com.mariamkatamashvili.gym.dto.userDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {
    @Valid
    @NotBlank
    private String username;

    @Valid
    @NotBlank
    private String password;
}
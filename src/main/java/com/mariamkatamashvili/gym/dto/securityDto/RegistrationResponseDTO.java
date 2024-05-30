package com.mariamkatamashvili.gym.dto.securityDto;

import com.mariamkatamashvili.gym.dto.userDto.LoginRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO {
    private LoginRequestDTO credentials;
    private TokenDTO token;
}
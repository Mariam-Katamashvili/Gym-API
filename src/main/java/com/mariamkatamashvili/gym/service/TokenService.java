package com.mariamkatamashvili.gym.service;

import com.mariamkatamashvili.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvili.gym.security.GymUserDetails;

public interface TokenService {
    RegistrationResponseDTO register(GymUserDetails user, String username, String password);
}
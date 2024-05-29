package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.securityDto.RegistrationResponseDTO;
import com.mariamkatamashvlii.gym.security.GymUserDetails;

public interface TokenService {
    RegistrationResponseDTO register(GymUserDetails user, String username, String password);
}
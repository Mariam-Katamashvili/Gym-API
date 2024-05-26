package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.securityDto.TokenDTO;
import com.mariamkatamashvlii.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequestDTO;
import lombok.Generated;

@Generated
public interface UserService {
    TokenDTO login(LoginRequestDTO loginRequest);

    TokenDTO changePassword(NewPasswordRequestDTO newPasswordRequest);
}
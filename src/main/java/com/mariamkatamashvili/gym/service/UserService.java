package com.mariamkatamashvili.gym.service;

import com.mariamkatamashvili.gym.dto.securityDto.TokenDTO;
import com.mariamkatamashvili.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvili.gym.dto.userDto.NewPasswordRequestDTO;
import lombok.Generated;

@Generated
public interface UserService {
    TokenDTO login(LoginRequestDTO loginRequest);

    TokenDTO changePassword(NewPasswordRequestDTO newPasswordRequest);
}
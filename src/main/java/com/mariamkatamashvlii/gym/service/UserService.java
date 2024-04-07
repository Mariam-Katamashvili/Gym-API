package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequestDTO;
import lombok.Generated;

@Generated
public interface UserService {
    String login(LoginRequestDTO loginRequest);

    void changePassword(NewPasswordRequestDTO newPasswordRequest);
}
package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequestDTO;
import lombok.Generated;

@Generated
public interface UserService {
    boolean login(LoginRequestDTO loginRequestDTO);

    void changePassword(NewPasswordRequestDTO newPasswordRequestDTO);
}
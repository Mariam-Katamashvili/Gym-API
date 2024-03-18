package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.userDto.LoginDTO;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordDTO;
import lombok.Generated;

@Generated
public interface UserService {
    boolean login(LoginDTO loginDTO);

    void changePassword(NewPasswordDTO newPasswordDTO);
}
package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequestDTO;
import com.mariamkatamashvlii.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequestDTO loginRequestDTO) {
        userService.login(loginRequestDTO);
        return ResponseEntity.ok("Logged in successfully");
    }

    @PutMapping("/{username}/update-password")
    public ResponseEntity<String> passChange(
            @RequestBody NewPasswordRequestDTO newPassword) {
        userService.changePassword(newPassword);
        return ResponseEntity.ok("Password Changed Successfully");
    }
}
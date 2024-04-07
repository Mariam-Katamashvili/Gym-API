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

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody LoginRequestDTO loginRequest) {
        String token = userService.login(loginRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Logged in successfully");
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{username}/update-password")
    public ResponseEntity<String> passChange(
            @RequestBody NewPasswordRequestDTO newPassword) {
        userService.changePassword(newPassword);
        return ResponseEntity.ok("Password Changed Successfully");
    }
}
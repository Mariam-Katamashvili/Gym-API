package com.mariamkatamashvili.gym.controller;

import com.mariamkatamashvili.gym.dto.securityDto.TokenDTO;
import com.mariamkatamashvili.gym.dto.userDto.LoginRequestDTO;
import com.mariamkatamashvili.gym.dto.userDto.NewPasswordRequestDTO;
import com.mariamkatamashvili.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        TokenDTO token = userService.login(loginRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Logged in successfully");
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{username}/update-password")
    public ResponseEntity<String> passChange(
            @PathVariable("username") String username,
            @RequestBody NewPasswordRequestDTO newPassword) {
        userService.changePassword(newPassword);
        return ResponseEntity.ok("Password Changed Successfully");
    }
}
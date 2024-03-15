package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password) {
        boolean isAuthorized = userService.login(username, password);
        if (isAuthorized) {
            return ResponseEntity.ok("Logged in successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PutMapping("/passChange")
    public ResponseEntity<String> passChange(
            @RequestParam String username,
            @RequestParam String currPassword,
            @RequestParam String newPassword) {
        boolean isAuthorized = userService.login(username, currPassword);
        if (isAuthorized) {
            userService.passChange(username, currPassword, newPassword);
            return ResponseEntity.ok("Password Changed Successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}
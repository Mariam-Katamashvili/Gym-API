package com.mariamkatamashvili.gym.controller;

import com.mariamkatamashvili.gym.exception.GymException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(GymException.class)
    public ResponseEntity<String> handleGymException(GymException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
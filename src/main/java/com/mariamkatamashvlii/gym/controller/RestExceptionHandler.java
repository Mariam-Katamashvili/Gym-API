package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.exception.UserNotCreatedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestExceptionHandler {
    @ExceptionHandler(UserNotCreatedException.class)
    public ResponseEntity<String> userNotCreatedException(UserNotCreatedException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}

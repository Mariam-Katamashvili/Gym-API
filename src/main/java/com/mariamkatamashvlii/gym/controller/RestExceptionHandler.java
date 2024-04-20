package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.exception.AuthenticationException;
import com.mariamkatamashvlii.gym.exception.TraineeNotFoundException;
import com.mariamkatamashvlii.gym.exception.TrainerNotFoundException;
import com.mariamkatamashvlii.gym.exception.TrainingTypeFetchException;
import com.mariamkatamashvlii.gym.exception.TrainingTypeNotFoundException;
import com.mariamkatamashvlii.gym.exception.UserNotCreatedException;
import com.mariamkatamashvlii.gym.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(UserNotCreatedException.class)
    public ResponseEntity<String> userNotCreatedException(UserNotCreatedException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(TrainingTypeFetchException.class)
    public ResponseEntity<String> trainingTypeFetchException(TrainingTypeFetchException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(TraineeNotFoundException.class)
    public ResponseEntity<String> handleTraineeNotFoundException(TraineeNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    public ResponseEntity<Object> handleTrainerNotFoundException(TrainerNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(com.mariamkatamashvlii.gym.exception.AuthenticationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(TrainingTypeNotFoundException.class)
    public ResponseEntity<String> handleTrainingTypeNotFoundException(TrainingTypeNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
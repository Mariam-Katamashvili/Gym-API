package com.mariamkatamashvlii.gym.validator;

import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.exception.GymException;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Validator {
    private final TraineeRepository traineeRepo;
    private final TrainerRepository trainerRepo;
    private final UserRepository userRepo;

    public void validateTraineeExists(String username) {
        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null) {
            throw new GymException("Trainee not found for username - " + username);
        }
    }
    public void validateTrainerExists(String username) {
        Trainer trainer = trainerRepo.findByUsername(username);
        if (trainer == null) {
            throw new GymException("Trainer not found for username - " + username);
        }
    }

    public void validateUserExists(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new GymException("User not found"));
        if (user == null) {
            throw new GymException("User not found for username: " + username);
        }
    }

    public void validateFutureDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new GymException("Training date must be in the future.");
        }
    }
}
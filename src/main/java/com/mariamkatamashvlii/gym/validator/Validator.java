package com.mariamkatamashvlii.gym.validator;

import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.exception.TraineeNotFoundException;
import com.mariamkatamashvlii.gym.exception.TrainerNotFoundException;
import com.mariamkatamashvlii.gym.exception.UserNotFoundException;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {
    private final TraineeRepository traineeRepo;
    private final TrainerRepository trainerRepo;
    private final UserRepository userRepo;

    public void validateTraineeExists(String username) {
        Trainee trainee = traineeRepo.findByUsername(username);
        if (trainee == null) {
            throw new TraineeNotFoundException("Trainee not found for username - " + username);
        }
    }
    public void validateTrainerExists(String username) {
        Trainer trainer = trainerRepo.findByUsername(username);
        if (trainer == null) {
            throw new TrainerNotFoundException("Trainer not found for username - " + username);
        }
    }

    public void validateUserExists(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found for username: " + username);
        }
    }
}
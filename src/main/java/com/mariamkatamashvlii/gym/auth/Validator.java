package com.mariamkatamashvlii.gym.auth;

import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.User;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    public void validateTrainee(Trainee trainee, User user) {
        if (trainee.getUser() == null || trainee.getUser().getId() == null) {
            throw new IllegalArgumentException("Trainee must be associated with a valid user.");
        }
        if (user == null) {
            throw new IllegalArgumentException("User does not exist.");
        }
    }

    public void validateTrainer(Trainer trainer, User user) {
        if (trainer.getUser() == null || trainer.getUser().getId() == null) {
            throw new IllegalArgumentException("Trainee must be associated with a valid user.");
        }
        if (trainer.getSpecialization() == null) {
            throw new IllegalArgumentException("Trainer specialization is required.");
        }
        if (user == null) {
            throw new IllegalArgumentException("User does not exist.");
        }
    }
}

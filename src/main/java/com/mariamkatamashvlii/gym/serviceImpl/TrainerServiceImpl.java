package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.repo.TrainerRepo;
import com.mariamkatamashvlii.gym.repo.TrainingTypeRepo;
import com.mariamkatamashvlii.gym.repo.UserRepo;
import com.mariamkatamashvlii.gym.model.Trainer;
import com.mariamkatamashvlii.gym.model.TrainingType;
import com.mariamkatamashvlii.gym.model.User;
import com.mariamkatamashvlii.gym.service.TrainerService;
import com.mariamkatamashvlii.gym.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    private TrainerRepo trainerRepo;
    private UserRepo userRepo;
    private TrainingTypeRepo trainingTypeRepo;
    private UserService userService;

    @Autowired
    public TrainerServiceImpl(TrainerRepo trainerRepo, UserRepo userRepo, TrainingTypeRepo trainingTypeRepo, UserService userService) {
        this.trainerRepo = trainerRepo;
        this.userRepo = userRepo;
        this.trainingTypeRepo = trainingTypeRepo;
        this.userService = userService;
    }

    @Override
    public void create(Trainer trainer) {
        trainerRepo.create(trainer);
    }

    @Override
    public void update(Trainer trainer) {
        trainerRepo.update(trainer);
    }

    @Override
    public void delete(long id) {
        trainerRepo.delete(id);
    }

    @Override
    public Trainer select(long id) {
        return trainerRepo.select(id);
    }

    @Override
    public Trainer select(String username) {
        return trainerRepo.select(username);
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        return userService.checkCredentials(username, password);
    }

    @Override
    public boolean changePassword(String username, String currPassword, String newPassword) {
        return userService.changePassword(username, currPassword, newPassword);
    }

    @Override
    public void toggleActivation(String username, boolean isActive) {
        userService.toggleActivation(username, isActive);
    }

    @Override
    public List<Trainer> findAll() {
        return trainerRepo.findAll();
    }

    public void createTrainerProfile(long trainingType, long user) {
        TrainingType type = trainingTypeRepo.select(trainingType);
        User usr = userRepo.select(user);
        Trainer trainer = new Trainer(type);
        trainer.setUser(usr);
        trainerRepo.create(trainer);
    }
}

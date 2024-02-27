package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.repo.TraineeRepo;
import com.mariamkatamashvlii.gym.repo.UserRepo;
import com.mariamkatamashvlii.gym.model.Trainee;
import com.mariamkatamashvlii.gym.model.User;
import com.mariamkatamashvlii.gym.service.TraineeService;
import com.mariamkatamashvlii.gym.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    private TraineeRepo traineeRepo;
    private UserRepo userRepo;
    private UserService userService;

    @Autowired
    public TraineeServiceImpl(TraineeRepo traineeRepo, UserRepo userRepo, UserService userService) {
        this.traineeRepo = traineeRepo;
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @Override
    public void create(Trainee trainee) {
        traineeRepo.create(trainee);
    }

    @Override
    public void update(Trainee trainee) {
        traineeRepo.update(trainee);
    }

    @Override
    public void delete(long id) {
        traineeRepo.delete(id);
    }

    @Override
    public void delete(String username) {
        traineeRepo.delete(username);
    }

    @Override
    public Trainee select(long id) {
        return traineeRepo.select(id);
    }

    @Override
    public Trainee select(String username) {
        return traineeRepo.select(username);
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
    public List<Trainee> findAll() {
        return traineeRepo.findAll();
    }

    public void createTraineeProfile(Date dob, String address, long userId) {
        Trainee trainee = new Trainee(dob, address);
        User user = userRepo.select(userId);
        trainee.setUser(user);
        create(trainee);
    }
}

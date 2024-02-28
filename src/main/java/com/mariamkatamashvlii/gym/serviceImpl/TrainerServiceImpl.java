package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.model.Training;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        User user = userRepo.select(trainer.getUser().getUserId());
        if (trainer.getSpecialization() != null && user != null) {
            trainerRepo.create(trainer);
        }
    }

    @Override
    public void update(Trainer trainer) {
        User user = userRepo.select(trainer.getUser().getUserId());
        if (trainer.getSpecialization() != null && user != null) {
            if (userService.checkCredentials(trainer.getUser().getUsername(), trainer.getUser().getPassword())) {
                trainerRepo.update(trainer);
            }
        }
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
    public Trainer select(String username, String password) {
        if (userService.checkCredentials(username, password)) {
            return trainerRepo.select(username);
        }
        return null;
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
    public void toggleActivation(String username, String password, boolean isActive) {
        if (userService.checkCredentials(username, password)) {
            userService.toggleActivation(username, isActive);
        }
    }

    @Override
    public List<Trainer> findAll() {
        return trainerRepo.findAll();
    }

    @Override
    public void createTrainerProfile(long trainingType, long user) {
        TrainingType type = trainingTypeRepo.select(trainingType);
        User usr = userRepo.select(user);
        Trainer trainer = new Trainer(type);
        trainer.setUser(usr);
        trainerRepo.create(trainer);
    }

    @Override
    public List<Training> getTrainingList(String username, String password, Date fromDate, Date toDate, String traineeName) {
        List<Training> trainingList = new ArrayList<>();
        if (userService.checkCredentials(username, password)) {
            Trainer trainer = trainerRepo.select(username);
            Set<Training> trainingSet = trainer.getTrainingSet();
            for (Training t : trainingSet) {
                if (isBetween(t.getTrainingDate(), fromDate, toDate) &&
                        t.getTrainee().getUser().getFirstName().equals(traineeName)) {
                    trainingList.add(t);
                }
            }
        }
        return trainingList;
    }

    public boolean isBetween(Date trainingdate, Date fromDate, Date toDate) {
        return trainingdate.compareTo(fromDate) >= 0 && trainingdate.compareTo(toDate) <= 0;
    }
}

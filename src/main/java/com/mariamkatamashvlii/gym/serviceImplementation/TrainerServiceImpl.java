package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.Training;
import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
import com.mariamkatamashvlii.gym.repository.UserRepository;
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
public class TrainerService implements com.mariamkatamashvlii.gym.service.TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);
    private final TrainerRepository trainerRepo;
    private final UserRepository userRepo;
    private final TrainingTypeRepository trainingTypeRepo;
    private final UserService userService;

    @Autowired
    public TrainerService(TrainerRepository trainerRepo, UserRepository userRepo, TrainingTypeRepository trainingTypeRepo, UserService userService) {
        this.trainerRepo = trainerRepo;
        this.userRepo = userRepo;
        this.trainingTypeRepo = trainingTypeRepo;
        this.userService = userService;
        logger.debug("TrainerServiceImpl initialized with TrainerRepo, UserRepo, TrainingTypeRepo and UserService");
    }

    @Override
    public void create(Trainer trainer) {
        User user = userRepo.select(trainer.getUser().getUserId());
        if (trainer.getSpecialization() != null && user != null) {
            trainerRepo.create(trainer);
            logger.info("Created trainer with id {}", trainer.getTrainerId());
        }
    }

    @Override
    public void update(Trainer trainer) {
        User user = userRepo.select(trainer.getUser().getUserId());
        if (trainer.getSpecialization() != null && user != null && (userService.checkCredentials(trainer.getUser().getUsername(), trainer.getUser().getPassword()))) {
            trainerRepo.update(trainer);
            logger.info("Updated trainer with id {}", trainer.getTrainerId());

        }
    }

    @Override
    public void delete(long id) {
        trainerRepo.delete(id);
        logger.info("Deleted trainer with id {}", id);
    }

    @Override
    public Trainer select(long id) {
        logger.info("Selecting trainer with id {}", id);
        return trainerRepo.select(id);
    }

    @Override
    public Trainer select(String username, String password) {
        if (userService.checkCredentials(username, password)) {
            logger.info("Selecting trainer with username - {}", username);
            return trainerRepo.select(username);
        }
        logger.info("Could not select trainer with username - {}", username);
        return null;
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        logger.info("Checking credentials for trainer {}", username);
        return userService.checkCredentials(username, password);
    }

    @Override
    public boolean changePassword(String username, String currPassword, String newPassword) {
        logger.info("Changing password for trainer {}", username);
        return userService.changePassword(username, currPassword, newPassword);
    }

    @Override
    public void toggleActivation(String username, String password, boolean isActive) {
        if (userService.checkCredentials(username, password)) {
            userService.toggleActivation(username, isActive);
            logger.info("Changed activation status for trainer {}", username);
        }
    }

    @Override
    public List<Trainer> findAll() {
        logger.info("Selecting all trainers");
        return trainerRepo.findAll();
    }

    @Override
    public void createTrainerProfile(long trainingType, long user) {
        TrainingType type = trainingTypeRepo.select(trainingType);
        User usr = userRepo.select(user);
        Trainer trainer = new Trainer(type);
        trainer.setUser(usr);
        trainerRepo.create(trainer);
        logger.info("Created trainer profile with id {}", trainer.getTrainerId());
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
        logger.info("Returning training list for trainer {}", username);
        return trainingList;
    }

    public boolean isBetween(Date trainingdate, Date fromDate, Date toDate) {
        return trainingdate.compareTo(fromDate) >= 0 && trainingdate.compareTo(toDate) <= 0;
    }
}

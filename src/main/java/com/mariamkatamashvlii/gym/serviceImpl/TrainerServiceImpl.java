package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.dao.TrainerDao;
import com.mariamkatamashvlii.gym.dao.TrainingTypeDao;
import com.mariamkatamashvlii.gym.dao.UserDao;
import com.mariamkatamashvlii.gym.daoImpl.TrainerDaoImpl;
import com.mariamkatamashvlii.gym.model.Trainer;
import com.mariamkatamashvlii.gym.model.TrainingType;
import com.mariamkatamashvlii.gym.model.User;
import com.mariamkatamashvlii.gym.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    private TrainerDao trainerDao;
    private UserDao userDao;
    private TrainingTypeDao trainingTypeDao;

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao, UserDao userDao, TrainingTypeDao trainingTypeDao) {
        this.trainerDao = trainerDao;
        this.userDao = userDao;
        this.trainingTypeDao = trainingTypeDao;
    }


    @Override
    public void create(Trainer trainer) {
        trainerDao.create(trainer);
    }

    @Override
    public void update(Trainer trainer) {
        trainerDao.update(trainer);
    }

    @Override
    public void delete(long id) {
        trainerDao.delete(id);
    }

    @Override
    public Trainer select(long id) {
        return trainerDao.select(id);
    }

    @Override
    public List<Trainer> findAll() {
        return trainerDao.findAll();
    }

    public void createTrainerProfile(long trainingType, long user) {
        TrainingType type = trainingTypeDao.select(trainingType);
        User usr = userDao.select(user);
        Trainer trainer = new Trainer(type);
        trainer.setUser(usr);
        trainerDao.create(trainer);
    }
}

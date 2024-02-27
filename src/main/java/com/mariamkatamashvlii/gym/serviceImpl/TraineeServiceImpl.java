package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.dao.TraineeDao;
import com.mariamkatamashvlii.gym.dao.UserDao;
import com.mariamkatamashvlii.gym.daoImpl.TraineeDaoImpl;
import com.mariamkatamashvlii.gym.model.Trainee;
import com.mariamkatamashvlii.gym.model.User;
import com.mariamkatamashvlii.gym.service.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    private TraineeDao traineeDao;
    private UserDao userDao;
    @Autowired
    public TraineeServiceImpl(TraineeDao traineeDao, UserDao userDao) {
        this.traineeDao = traineeDao;
        this.userDao = userDao;
    }

    @Override
    public void create(Trainee trainee) {
        traineeDao.create(trainee);
    }

    @Override
    public void update(Trainee trainee) {
        traineeDao.update(trainee);
    }

    @Override
    public void delete(long id) {
        traineeDao.delete(id);
    }

    @Override
    public Trainee select(long id) {
        return traineeDao.select(id);
    }

    @Override
    public List<Trainee> findAll() {
        return traineeDao.findAll();
    }

    public void createTraineeProfile(Date dob, String address, long userId) {
        Trainee trainee = new Trainee(dob, address);
        User user = userDao.select(userId);
        trainee.setUser(user);
        create(trainee);
    }
}

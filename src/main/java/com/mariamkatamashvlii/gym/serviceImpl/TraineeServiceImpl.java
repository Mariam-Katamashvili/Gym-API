package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.daoImpl.TraineeDaoImpl;
import com.mariamkatamashvlii.gym.model.Trainee;
import com.mariamkatamashvlii.gym.service.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TraineeServiceImpl implements TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    private TraineeDaoImpl traineeDaoImpl;

    @Autowired
    public void setTraineeDaoImpl(TraineeDaoImpl traineeDaoImpl) {
        this.traineeDaoImpl = traineeDaoImpl;
    }

    @Override
    public void create(Trainee trainee) {
        logger.info("Creating trainee with ID {}", trainee.getUser().getUserId());
        traineeDaoImpl.create(trainee);
    }

    @Override
    public void update(long traineeId, Trainee trainee) {
        logger.info("Updating trainee with ID {}", traineeId);
        traineeDaoImpl.update(traineeId, trainee);
    }

    @Override
    public void delete (long traineeId) {
        logger.info("Deleting trainee with ID {}", traineeId);
        traineeDaoImpl.delete(traineeId);
    }

    @Override
    public Trainee select(long traineeId) {
        logger.info("Selecting trainee with ID {}", traineeId);
        return traineeDaoImpl.select(traineeId);
    }

}

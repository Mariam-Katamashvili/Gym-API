package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.daoImpl.TrainerDaoImpl;
import com.mariamkatamashvlii.gym.model.Trainer;
import com.mariamkatamashvlii.gym.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TrainerServiceImpl implements TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    private TrainerDaoImpl trainerDaoImpl;

    @Autowired
    public void setTrainerDaoImpl(TrainerDaoImpl trainerDaoImpl) {
        this.trainerDaoImpl = trainerDaoImpl;
    }

    @Override
    public void create(Trainer trainer) {
        logger.info("Creating trainer with ID {}", trainer.getUserId());
        trainerDaoImpl.create(trainer);
    }

    @Override
    public void update(long trainerId, Trainer trainer) {
        logger.info("Updating trainer with ID {}", trainerId);
        trainerDaoImpl.update(trainerId, trainer);
    }

    @Override
    public Trainer select(long trainerId) {
        logger.info("Selecting trainer with ID {}", trainerId);
        return trainerDaoImpl.select(trainerId);
    }
}

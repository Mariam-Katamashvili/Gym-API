package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.dao.TrainingDao;
import com.mariamkatamashvlii.gym.daoImpl.TrainingDaoImpl;
import com.mariamkatamashvlii.gym.model.Training;
import com.mariamkatamashvlii.gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService{
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private TrainingDao trainingDao;

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }


    @Override
    public void create(Training training) {
        trainingDao.create(training);
    }

    @Override
    public void update(Training training) {
        trainingDao.update(training);
    }

    @Override
    public void delete(long id) {
        trainingDao.delete(id);
    }

    @Override
    public Training select(long id) {
        return trainingDao.select(id);
    }

    @Override
    public List<Training> findAll() {
        return trainingDao.findAll();
    }
}

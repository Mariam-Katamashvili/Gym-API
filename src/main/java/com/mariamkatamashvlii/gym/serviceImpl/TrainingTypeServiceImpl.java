package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.dao.TrainingTypeDao;
import com.mariamkatamashvlii.gym.model.TrainingType;
import com.mariamkatamashvlii.gym.service.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private TrainingTypeDao trainingTypeDao;

    @Autowired
    public TrainingTypeServiceImpl(TrainingTypeDao trainingTypeDao) {
        this.trainingTypeDao = trainingTypeDao;
    }

    @Override
    public void create(TrainingType trainingType) {
        trainingTypeDao.create(trainingType);
    }

    @Override
    public void update(TrainingType trainingType) {
        trainingTypeDao.update(trainingType);
    }

    @Override
    public void delete(long id) {
        trainingTypeDao.delete(id);
    }

    @Override
    public TrainingType select(long id) {
        return trainingTypeDao.select(id);
    }

    @Override
    public List<TrainingType> findAll() {
        return trainingTypeDao.findAll();
    }
}

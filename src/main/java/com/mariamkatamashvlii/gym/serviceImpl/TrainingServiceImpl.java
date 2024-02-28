package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.repo.TrainingRepo;
import com.mariamkatamashvlii.gym.model.Training;
import com.mariamkatamashvlii.gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private TrainingRepo trainingRepo;

    @Autowired
    public void setTrainingDao(TrainingRepo trainingRepo) {
        this.trainingRepo = trainingRepo;
    }


    @Override
    public void create(Training training) {
        trainingRepo.create(training);
    }

    @Override
    public void update(Training training) {
        trainingRepo.update(training);
    }

    @Override
    public void delete(long id) {
        trainingRepo.delete(id);
    }

    @Override
    public Training select(long id) {
        return trainingRepo.select(id);
    }

    @Override
    public List<Training> findAll() {
        return trainingRepo.findAll();
    }
}

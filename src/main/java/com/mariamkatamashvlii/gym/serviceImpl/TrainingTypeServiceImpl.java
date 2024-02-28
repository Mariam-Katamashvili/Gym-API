package com.mariamkatamashvlii.gym.serviceImpl;

import com.mariamkatamashvlii.gym.repo.TrainingTypeRepo;
import com.mariamkatamashvlii.gym.model.TrainingType;
import com.mariamkatamashvlii.gym.service.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private TrainingTypeRepo trainingTypeRepo;

    @Autowired
    public TrainingTypeServiceImpl(TrainingTypeRepo trainingTypeRepo) {
        this.trainingTypeRepo = trainingTypeRepo;
    }

    @Override
    public TrainingType select(long id) {
        return trainingTypeRepo.select(id);
    }

    @Override
    public List<TrainingType> findAll() {
        return trainingTypeRepo.findAll();
    }
}

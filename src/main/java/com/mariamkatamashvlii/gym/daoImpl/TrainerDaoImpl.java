package com.mariamkatamashvlii.gym.daoImpl;

import com.mariamkatamashvlii.gym.dao.TrainerDao;
import com.mariamkatamashvlii.gym.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainerDaoImpl implements TrainerDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);
    private Map<Long, Trainer> trainerMap;

    @Autowired
    public void setTrainerMap(Map<Long, Trainer> trainerMap) {
        this.trainerMap = trainerMap;
    }

    public void create(Trainer trainer) {
        long id = trainer.getUser().getUserId();
        if(trainerMap.containsKey(id)) {
            logger.error("Trainer with ID {} already exists!", id);
            return;
        }
        trainerMap.put(id, trainer);
        logger.info("Trainer with ID {} created successfully!", id);
    }
    public void update(long id, Trainer trainer) {
        if(!trainerMap.containsKey(id)) {
            logger.info("Trainer with ID {} does not exist, creating a new one.", id);
            create(trainer);
        } else {
            trainerMap.put(id, trainer);
            logger.info("Trainer with ID {} updated successfully!", id);
        }

    }
    public Trainer select(long id) {
        if(!trainerMap.containsKey(id)) {
            logger.error("Trainer with ID {} does not exist!", id);
            return null;
        }
        return trainerMap.get(id);
    }

}

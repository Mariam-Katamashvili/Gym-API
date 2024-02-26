package com.mariamkatamashvlii.gym.daoImpl;

import com.mariamkatamashvlii.gym.dao.TraineeDao;
import com.mariamkatamashvlii.gym.model.Trainee;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.WrappedNClob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeDaoImpl implements TraineeDao {
    private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);

    @Autowired
    private EntityManager entityManager;

    @Override
    public void create(Trainee trainee) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(trainee);
        logger.info("Created trainee with id {}", trainee.getTraineeId());
    }

    @Override
    public void update(Trainee trainee) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(trainee);
        logger.info("Updated trainee with id {}", trainee.getTraineeId());
    }

    @Override
    public void delete(long traineeId) {
        Session session = entityManager.unwrap(Session.class);
        Trainee trainee = session.get(Trainee.class, traineeId);
        session.remove(trainee);
    }

    @Override
    public Trainee select(long traineeId) {
        return null;
    }
}
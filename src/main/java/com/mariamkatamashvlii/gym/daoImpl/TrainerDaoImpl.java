package com.mariamkatamashvlii.gym.daoImpl;

import com.mariamkatamashvlii.gym.dao.TrainerDao;
import com.mariamkatamashvlii.gym.model.Trainer;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TrainerDaoImpl implements TrainerDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);

    private EntityManager entityManager;
    @Autowired
    public TrainerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Trainer trainer) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(trainer);
        logger.info("Created trainer with id {}", trainer.getTrainerId());
    }

    @Override
    public void update(Trainer trainer) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(trainer);
        logger.info("Updated trainer with id {}", trainer.getTrainerId());
    }

    @Override
    public void delete(long trainerId) {
        Session session = entityManager.unwrap(Session.class);
        Trainer trainer = session.get(Trainer.class, trainerId);
        session.remove(trainer);
    }

    @Override
    public Trainer select(long trainerId) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Trainer.class, trainerId);
    }

    @Override
    public List<Trainer> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Trainer> query = session.createQuery("from Trainer ", Trainer.class);
        List<Trainer> trainerList = query.getResultList();
        return trainerList;
    }
}

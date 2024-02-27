package com.mariamkatamashvlii.gym.daoImpl;

import com.mariamkatamashvlii.gym.dao.TrainingDao;
import com.mariamkatamashvlii.gym.model.Training;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);

    private EntityManager entityManager;
    @Autowired
    public TrainingDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Training training) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(training);
    }

    @Override
    public void update(Training training) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(training);
    }

    @Override
    public void delete(long trainingId) {
        Session session = entityManager.unwrap(Session.class);
        Training training = session.get(Training.class, trainingId);
        session.remove(training);
    }

    @Override
    public Training select(long trainingId) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Training.class, trainingId);
    }

    @Override
    public List<Training> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Training> query = session.createQuery("from Training ", Training.class);
        List<Training> trainingList = query.getResultList();
        return trainingList;
    }
}

package com.mariamkatamashvlii.gym.daoImpl;

import com.mariamkatamashvlii.gym.dao.TrainingTypeDao;
import com.mariamkatamashvlii.gym.model.TrainingType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Queue;

@Repository
public class TrainingTypeDaoImpl implements TrainingTypeDao {
    private EntityManager entityManager;

    @Autowired
    public TrainingTypeDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(TrainingType trainingType) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(trainingType);
    }

    @Override
    public void update(TrainingType trainingType) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(trainingType);
    }

    @Override
    public void delete(long trainingTypeId) {
        Session session = entityManager.unwrap(Session.class);
        TrainingType trainingType = session.get(TrainingType.class, trainingTypeId);
    }

    @Override
    public TrainingType select(long trainingTypeId) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(TrainingType.class, trainingTypeId);
    }

    @Override
    public List<TrainingType> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<TrainingType> query = session.createQuery("from TrainingType ", TrainingType.class);
        List<TrainingType> trainingTypeList = query.getResultList();
        return trainingTypeList;
    }
}

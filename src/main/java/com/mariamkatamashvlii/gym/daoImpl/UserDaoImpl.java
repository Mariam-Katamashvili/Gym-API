package com.mariamkatamashvlii.gym.daoImpl;

import com.mariamkatamashvlii.gym.dao.UserDao;
import com.mariamkatamashvlii.gym.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);

    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(user);
    }

    @Override
    public void update(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(user);
    }

    @Override
    public void delete(long userId) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, userId);
        session.remove(user);
    }

    @Override
    public User select(long userId) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(User.class, userId);
    }

    @Override
    public List<User> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("from User ", User.class);
        List<User> userList = query.getResultList();
        return userList;
    }
}

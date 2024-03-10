package com.mariamkatamashvlii.gym.repositoryImplementation;

import com.mariamkatamashvlii.gym.entity.User;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepository implements com.mariamkatamashvlii.gym.repository.UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private final ApplicationContext applicationContext;
    private final EntityManager entityManager;

    @Autowired
    public UserRepository(ApplicationContext applicationContext, EntityManager entityManager) {
        this.applicationContext = applicationContext;
        this.entityManager = entityManager;
        logger.debug("UserRepoImpl initialized with ApplicationContext and EntityManager");
    }

    @Override
    @Transactional
    public User create(User user) {
        Session session = entityManager.unwrap(Session.class);
        logger.info("Created user with id {}", user.getUserId());
        session.persist(user);
        return user;
    }

    @Override
    @Transactional
    public void update(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(user);
        logger.info("Updated user with id {}", user.getUserId());
    }

    @Override
    @Transactional
    public void delete(long id) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, id);
        session.remove(user);
        logger.info("Deleted user with id {}", user.getUserId());
    }

    @Override
    @Transactional
    public void delete(String username) {
        Session session = entityManager.unwrap(Session.class);
        UserRepository dao = applicationContext.getBean(UserRepository.class);
        User user = dao.select(username);
        session.remove(user);
        logger.info("Deleted user with username {}", user.getUsername());
    }

    @Override
    @Transactional
    public User select(long id) {
        Session session = entityManager.unwrap(Session.class);
        logger.info("Selecting user with id {}", id);
        return session.get(User.class, id);
    }

    @Override
    @Transactional
    public User select(String username) {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("from User where username = :username ", User.class);
        query.setParameter("username", username);
        logger.info("Selecting user with username {}", username);
        return query.uniqueResult();
    }

    @Override
    @Transactional
    public List<User> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("from User ", User.class);
        List<User> users = query.getResultList();
        logger.info("Returning all users");
        return users;
    }
}

package com.github.demoapp.repository.impl;


import com.github.demoapp.exception.NotUniqueEmailException;
import com.github.demoapp.model.User;
import com.github.demoapp.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.RollbackException;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

import static com.github.demoapp.util.EntityManagerProvider.getEntityManager;


public class UserRepositoryImpl implements UserRepository {

    private static void throwsRollbackException(Exception e) throws NotUniqueEmailException {
        if (e instanceof RollbackException) {
            throw new NotUniqueEmailException("this email is taken");
        }
    }

    @Override
    public Optional<User> save(User user) throws NotUniqueEmailException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throwsRollbackException(e);
        } finally {
            em.close();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> update(User user) throws NotUniqueEmailException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throwsRollbackException(e);
        } finally {
            em.close();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return Optional.ofNullable(em.find(User.class, id));
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email",
                    User.class
            );
            query.setParameter("email", email);
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

}

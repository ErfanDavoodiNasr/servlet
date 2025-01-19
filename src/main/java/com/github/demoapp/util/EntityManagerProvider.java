package com.github.demoapp.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.experimental.UtilityClass;


@UtilityClass
public class EntityManagerProvider {
    private EntityManagerFactory emf;
    private final Object object = new Object();

    public synchronized EntityManager getEntityManager() {
        if (emf == null) synchronized (object) {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("jdbc-postgres");
            }
        }

        return emf.createEntityManager();
    }
}

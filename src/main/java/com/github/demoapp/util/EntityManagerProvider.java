package com.github.demoapp.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.experimental.UtilityClass;


@UtilityClass
public class EntityManagerProvider {
    private static final Object object = new Object();
    private static EntityManagerFactory emf;

    public static synchronized EntityManager getEntityManager() {
        if (emf == null) synchronized (object) {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("jdbc-postgres");
            }
        }

        return emf.createEntityManager();
    }
}

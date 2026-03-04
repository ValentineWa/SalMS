package com.salms.salms.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    // Create the EntityManagerFactory once
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("myPU"); // matches persistence-unit name

    // Provide access to it
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    // Optional: method to close it when app shuts down
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

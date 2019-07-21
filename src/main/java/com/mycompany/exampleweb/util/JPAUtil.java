package com.mycompany.exampleweb.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class JPAUtil implements ServletContextListener {

    private static EntityManagerFactory emf;
    private static final String PERSISTENCE_UNIT = "PERSISTENCE_UNIT";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        createFactory();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        closeFactory();
    }

    private static void createFactory() {

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeFactory() {
        emf.close();
    }

    public static EntityManager getEntityManager() {

        if (emf == null) {
            createFactory();
        }

        return emf.createEntityManager();
    }

}

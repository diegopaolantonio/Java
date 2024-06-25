package managers;

import jakarta.persistence.*;

public class Manager {

    private static final String PERSISTENCE_UNIT_NAME = "JavaPaolantonioPU";
    private static EntityManagerFactory factory;
    static {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static EntityManager get() {
        return factory.createEntityManager();
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    public static void closeEntity(EntityManager entityManager) {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

}

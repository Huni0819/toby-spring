package tobyspring.hellospring.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class JpaTemplate {

    private final EntityManagerFactory emf;

    public JpaTemplate(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public <T> void access(T data, JpaCallback<T> callback) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            callback.execute(data, em);

            transaction.commit();
        } catch (RuntimeException e) {

            if (transaction.isActive()){
                transaction.rollback();
            }
            throw e;
        } finally {

            if (em.isOpen()) {
                em.close();
            }
        }
    }
}

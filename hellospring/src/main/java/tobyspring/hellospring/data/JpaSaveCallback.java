package tobyspring.hellospring.data;

import jakarta.persistence.EntityManager;

public class JpaSaveCallback<T> implements JpaCallback<T> {

    @Override
    public void execute(T data, EntityManager entityManager) {
        entityManager.persist(data);
    }
}

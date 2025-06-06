package tobyspring.hellospring.data;

import jakarta.persistence.EntityManager;

public interface JpaCallback<T> {

    void execute(T data, EntityManager entityManager);
}

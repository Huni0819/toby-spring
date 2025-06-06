package tobyspring.hellospring.data;

import tobyspring.hellospring.order.Order;


public class OrderRepository {

    private final JpaTemplate jpaTemplate;

    public OrderRepository(JpaTemplate jpaTemplate) {
        this.jpaTemplate = jpaTemplate;
    }

    public void save(Order order) {
        jpaTemplate.access(order, new JpaSaveCallback<Order>());
    }
}

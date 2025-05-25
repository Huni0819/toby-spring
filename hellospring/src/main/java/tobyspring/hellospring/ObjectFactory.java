package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectFactory {

    @Bean
    public PaymentService paymentService() {

        return new PaymentService(this.exRateProvider());
    }

    @Bean
    public OrderService orderService() {

        return new OrderService(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {

        return new SimpleExRateProvider();
    }
}

class OrderService {

    ExRateProvider exRateProvider;

    public OrderService(ExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }
}
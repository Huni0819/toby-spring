package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectFactory {

    @Bean
    public PaymentService paymentService() {

        return new PaymentService(this.exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {

        return new SimpleExRateProvider();
    }
}

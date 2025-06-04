package tobyspring.hellospring.payment;

import java.math.BigDecimal;
import java.time.Clock;

public class PaymentService {

    private final ExRateProvider exRateProvider;
    private final Clock clock;

    public PaymentService(ExRateProvider exRateProvider, Clock clock) {

        this.exRateProvider = exRateProvider;
        this.clock = clock;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {

        return Payment.createPrepared(orderId, currency, foreignCurrencyAmount, this.exRateProvider,
            this.clock);
    }
}

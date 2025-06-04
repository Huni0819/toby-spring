package tobyspring.hellospring.payment;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentTest {

    Clock clock;
    ExRateProvider exRateProvider;

    @BeforeEach
    void setup() {

        clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        exRateProvider = new ExRateProviderStub(BigDecimal.valueOf(1_000));
    }

    @Test
    void createPrepared() {

        Payment payment = Payment.createPrepared(
            1L, "USD", BigDecimal.TEN, exRateProvider, clock
        );

        Assertions.assertThat(payment.getConvertedAmount())
            .isEqualByComparingTo(BigDecimal.valueOf(10_000));
        Assertions.assertThat(payment.getValidUntil())
            .isEqualTo(LocalDateTime.now(clock).plusMinutes(30));
    }

    @Test
    void isValid() {

        Payment payment = Payment.createPrepared(
            1L, "USD", BigDecimal.TEN, exRateProvider, clock
        );

        Assertions.assertThat(payment.isValid(clock)).isTrue();
        Assertions.assertThat(
            payment.isValid(Clock.offset(clock, Duration.of(30, ChronoUnit.MINUTES)))).isFalse();
    }
}

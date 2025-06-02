package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentServiceTest {

    Clock clock;

    @BeforeEach
    void setup() {

         this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증")
    void convertedAmount() throws IOException {

        testAmount(valueOf(500L), valueOf(5_000L), this.clock);
        testAmount(valueOf(1_000L), valueOf(10_000L), this.clock);
        testAmount(valueOf(3_000L), valueOf(30_000L), this.clock);
    }

    @Test
    void validUntil() throws IOException {

        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1_000L)), clock);
        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // 3. 원화환산금액의 유효시간 계산
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(payment.getValidUntil())
                .isEqualTo(expectedValidUntil);
    }

    private static Payment testAmount(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) throws IOException {
        // given
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

        // when
        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // then
        // 1. 환율정보 가져온다.
        assertThat(payment.getExRate()).isEqualByComparingTo(exRate);

        // 2. 원화환산 금액 계산
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
        return payment;
    }
}
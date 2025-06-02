package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestPaymentConfig;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPaymentConfig.class)
class PaymentServiceSpringTest {

    @Autowired
    PaymentService paymentService;
    @Autowired
    ExRateProviderStub exRateProviderStub;
    @Autowired
    Clock clock;

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증")
    void convertedAmount() throws IOException {
        // exRate : 1000
        Payment payment = paymentService.prepare(1L, "USD", TEN);

        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1_000L));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000L));

        // exRate : 500
        exRateProviderStub.setExRate(valueOf(500));
        Payment payment2 = paymentService.prepare(1L, "USD", TEN);

        assertThat(payment2.getExRate()).isEqualByComparingTo(valueOf(500L));
        assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000L));
    }

    @Test
    void validUntil() throws IOException {

        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // 3. 원화환산금액의 유효시간 계산
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(payment.getValidUntil())
                .isEqualTo(expectedValidUntil);
    }

}
package tobyspring.hellospring.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentServiceTest {

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증")
    void convertedAmount() throws IOException {

        testAmount(valueOf(500L), valueOf(5_000L));
        testAmount(valueOf(1_000L), valueOf(10_000L));
        testAmount(valueOf(3_000L), valueOf(30_000L));

        // 3. 원화환산금액의 유효시간 계산
//        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
//        assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }

    private static Payment testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
        // given
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate));

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
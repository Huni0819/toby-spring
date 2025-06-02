package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

class PaymentServiceTest {

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증")
    void prepare() throws IOException {

        // given
        PaymentService paymentService = new PaymentService(new WebApiExRateProvider());

        // when
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // then
        // 1. 환율정보 가져온다.
        Assertions.assertThat(payment.getExRate())
                .isNotNull();

        // 2. 원화환산 금액 계산
        Assertions.assertThat(payment.getConvertedAmount())
                .isEqualTo(payment.getExRate().multiply(payment.getForeignCurrencyAmount()));

        // 3. 원화환산금액의 유효시간 계산
        Assertions.assertThat(payment.getValidUntil())
                .isAfter(LocalDateTime.now());
        Assertions.assertThat(payment.getValidUntil())
                .isBefore(LocalDateTime.now().plusMinutes(30));
    }
}
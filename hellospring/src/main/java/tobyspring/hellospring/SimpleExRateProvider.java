package tobyspring.hellospring;

import java.math.BigDecimal;

public class SimpleExRateProvider {

    BigDecimal getExRate(String currency) {

        if (currency.equals("USD")) {
            return BigDecimal.valueOf(1000);
        }

        throw new IllegalArgumentException("지원되지 않는 통화입니다.");
    }
}

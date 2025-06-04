package tobyspring.hellospring.exrate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import tobyspring.hellospring.payment.ExRateProvider;

public class CachedExRateProvider implements ExRateProvider {

    private final ExRateProvider target;
    private BigDecimal cachedExRate;
    private LocalDateTime cachedExpiryTime;

    public CachedExRateProvider(ExRateProvider target) {
        this.target = target;
    }

    @Override
    public BigDecimal getExRate(String currency) {

        if (this.cachedExRate == null || cachedExpiryTime.isBefore(LocalDateTime.now())) {

            this.cachedExRate = this.target.getExRate(currency);
            cachedExpiryTime = LocalDateTime.now().plusSeconds(3L);

            System.out.println("Cache Updated");
        }

        return cachedExRate;
    }
}

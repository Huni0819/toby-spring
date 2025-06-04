package tobyspring.hellospring.exrate;

import java.math.BigDecimal;
import tobyspring.hellospring.api.ApiTemplate;
import tobyspring.hellospring.payment.ExRateProvider;

public class WebApiExRateProvider implements ExRateProvider {

    private final ApiTemplate apiTemplate;

    public WebApiExRateProvider(ApiTemplate apiTemplate) {
        this.apiTemplate = apiTemplate;
    }

    // 클라이언트 - 클라이언트가 콜백을 만들어서 템플릿을 호출한다.
    @Override
    public BigDecimal getExRate(String currency) {

        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getForExRate(url);
    }

}

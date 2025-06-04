package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import tobyspring.hellospring.api.ApiExecutor;
import tobyspring.hellospring.api.ErApiExRateExtractor;
import tobyspring.hellospring.api.ExRateExtractor;
import tobyspring.hellospring.api.SimpleApiExecutor;
import tobyspring.hellospring.payment.ExRateProvider;

public class WebApiExRateProvider implements ExRateProvider {

    // 클라이언트 - 클라이언트가 콜백을 만들어서 템플릿을 호출한다.
    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return runApiForExRate(url, new SimpleApiExecutor(), new ErApiExRateExtractor());
    }

    // 템플릿 - 변하지 않는 속성의 코드
    private static BigDecimal runApiForExRate(String url, ApiExecutor apiExecutor,
        ExRateExtractor exRateExtractor) {

        // Callback 참조 정보 생성
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // Callback 호출, 참조 정보 전달
        String response;
        try {
            response = apiExecutor.execute(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return exRateExtractor.extract(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

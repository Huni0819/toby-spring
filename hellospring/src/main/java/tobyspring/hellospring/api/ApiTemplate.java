package tobyspring.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class ApiTemplate {

    private final ApiExecutor apiExecutor;
    private final ExRateExtractor exRateExtractor;

    // 디폴트 콜백 설정
    public ApiTemplate() {
        this.apiExecutor = new HttpClientApiExecutor();
        this.exRateExtractor = new ErApiExRateExtractor();
    }

    public ApiTemplate(ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
        this.apiExecutor = apiExecutor;
        this.exRateExtractor = exRateExtractor;
    }

    // convenient method
    public BigDecimal getForExRate(String url) {

        return this.getForExRate(url, this.apiExecutor, this.exRateExtractor);
    }

    public BigDecimal getForExRate(String url, ApiExecutor apiExecutor) {

        return this.getForExRate(url, apiExecutor, this.exRateExtractor);
    }

    public BigDecimal getForExRate(String url, ExRateExtractor exRateExtractor) {

        return this.getForExRate(url, this.apiExecutor, exRateExtractor);
    }

    // 템플릿 - 변하지 않는 속성의 코드
    public BigDecimal getForExRate(String url, ApiExecutor apiExecutor,
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

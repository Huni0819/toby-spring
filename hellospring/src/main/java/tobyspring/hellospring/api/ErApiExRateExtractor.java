package tobyspring.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import tobyspring.hellospring.exrate.ExRateData;

public class ErApiExRateExtractor implements ExRateExtractor {

    @Override
    public BigDecimal extract(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        return data.rates().get("KRW");
    }
}

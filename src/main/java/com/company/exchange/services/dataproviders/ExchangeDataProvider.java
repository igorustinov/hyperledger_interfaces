package com.company.exchange.services.dataproviders;

import com.company.exchange.model.ExchangeRate;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ExchangeDataProvider {

    private String dailyUrl = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private String _90daysUrl = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";

    public List<ExchangeRate> retrieveMonthly() {
        List<ExchangeRate> xRates = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        Envelope envelope = restTemplate.getForObject(_90daysUrl, Envelope.class);
        return new ExchangeRateAdapter().translate(envelope);
    }

    public List<ExchangeRate> retrieveDaily() {
        RestTemplate restTemplate = new RestTemplate();
        Envelope envelope = restTemplate.getForObject(dailyUrl, Envelope.class);
        return new ExchangeRateAdapter().translate(envelope);
    }
}

package com.company.exchange.services.dataproviders;

import com.company.exchange.model.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ExchangeDataProvider {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${daily_rates_url}")
    private String dailyUrl;
    @Value("${monthly_rates_url}")
    private String monthlyUrl;

    public List<ExchangeRate> retrieveMonthly() throws DataRetrievalException {
        return retrieveRates(monthlyUrl);
    }

    public List<ExchangeRate> retrieveDaily() throws DataRetrievalException {
        return retrieveRates(dailyUrl);
    }

    public List<ExchangeRate> retrieveRates(String url) throws DataRetrievalException {
        final long start = System.nanoTime();
        try {
            RestTemplate restTemplate = new RestTemplate();
            Envelope envelope = restTemplate.getForObject(url, Envelope.class);
            final List<ExchangeRate> exchangeRates = new ExchangeRateAdapter().translate(envelope);
            log.info("Data retrieval successful, elapsed " + ((System.nanoTime() - start) / 1000000) + " ms");
            return exchangeRates;
        } catch (RestClientException e) {
            log.error("Failed to retrieve exchange rates");
            throw new DataRetrievalException(e);
        }
    }
}

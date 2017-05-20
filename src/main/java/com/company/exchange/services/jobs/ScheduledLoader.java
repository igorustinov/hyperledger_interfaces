package com.company.exchange.services.jobs;

import com.company.exchange.model.ExchangeRate;
import com.company.exchange.services.cache.ExchangeRatesDataStorage;
import com.company.exchange.services.dataproviders.DataRetrievalException;
import com.company.exchange.services.dataproviders.ExchangeDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ScheduledLoader {

    @Autowired
    private ExchangeRatesDataStorage cache;

    @Autowired
    private ExchangeDataProvider dataProvider;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "1 0 * * * MON-FRI")
    @PostConstruct
    public void reloadCache() {

        while (true) {
            final boolean success = getExchangeRates();
            if (success) {
                break;
            } else {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private boolean getExchangeRates() {

        try {
            List<ExchangeRate> exchangeRates = dataProvider.retrieveMonthly();
            cache.reload(exchangeRates);
        } catch (DataRetrievalException e) {
            log.error("Failed to retrieve exchange rates from backend. Will attempt to get daily rate", e);
            try {
                cache.add(dataProvider.retrieveDaily());
            } catch (DataRetrievalException e1) {
                log.error("Failed to retrieve daily exchange rates from backend.", e);
            }
            return false;
        }

        return true;
    }
}

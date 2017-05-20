package com.company.exchange.services.jobs;

import com.company.exchange.model.ExchangeRate;
import com.company.exchange.services.cache.ExchangeRatesDataStorage;
import com.company.exchange.services.dataproviders.DataRetrievalException;
import com.company.exchange.services.dataproviders.ExchangeDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ScheduledLoader {

    @Value("${reconnect_interval}")
    private long repeatInterval;

    @Autowired
    private ExchangeRatesDataStorage cache;

    @Autowired
    private ExchangeDataProvider dataProvider;

    private AtomicBoolean dailyRatesLoaded = new AtomicBoolean(false);
    private AtomicInteger monthlyRatesAttemptCounter = new AtomicInteger(0);

    @PostConstruct
    void initialLoad() {
        Thread initLoadThread = new Thread(new Runnable(){
            @Override
            public void run() {
                reloadCache();
                log.info("Initial load complete");
            }
        }, "init-load-thread");
        initLoadThread.setDaemon(true);
        initLoadThread.start();
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "${update_schedule}")
    public void reloadCache() {

        while (true) {
            final boolean success = getExchangeRates();
            if (success) {
                break;
            } else {
                try {
                    Thread.sleep(repeatInterval);
                } catch (InterruptedException e) {
                    log.error("Reload thread interrupted");
                }
            }
        }
    }

    private boolean getExchangeRates() {

        try {
            monthlyRatesAttemptCounter.incrementAndGet();
            List<ExchangeRate> exchangeRates = dataProvider.retrieveMonthly();

            cache.reload(exchangeRates);
        } catch (DataRetrievalException e) {

            final int counter = monthlyRatesAttemptCounter.get();
            log.error("Failed to retrieve exchange rates from backend. Attempt: " + counter, e);

            if (! dailyRatesLoaded.get()) {
                log.info("Will attempt to get daily rates");
                try {
                    cache.add(dataProvider.retrieveDaily());
                    dailyRatesLoaded.set(true);
                    log.info("Daily rate success");
                } catch (DataRetrievalException e1) {
                    log.error("Failed to retrieve daily exchange rates from backend.", e);
                }
            }
            return false;
        }

        return true;
    }

    void setDataProvider(ExchangeDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    void setCache(ExchangeRatesDataStorage cache) {
        this.cache = cache;
    }

    boolean isDailyRatesLoaded() {
        return dailyRatesLoaded.get();
    }

    int getMonthlyRatesAttemptCounter() {
        return monthlyRatesAttemptCounter.get();
    }

    void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }
}

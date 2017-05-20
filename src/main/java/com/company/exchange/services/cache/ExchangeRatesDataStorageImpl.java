package com.company.exchange.services.cache;

import com.company.exchange.model.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class ExchangeRatesDataStorageImpl implements ExchangeRatesDataStorage {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Map<LocalDate, Map<Currency, ExchangeRate>> cachedRates = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Optional<ExchangeRate> getExchangeRate(@NotNull LocalDate date, @NotNull Currency currency) {
        log.debug(String.format("params: [date: %s, currency: %s]", date, currency));

        lock.readLock().lock();
        try {
            final Map<Currency, ExchangeRate> currencyExchangeRateMap = cachedRates.get(date);
            if (currencyExchangeRateMap == null) {
                return Optional.empty();
            }

            final ExchangeRate exchangeRate = currencyExchangeRateMap.get(currency);

            if (exchangeRate == null) {
                return Optional.empty();
            }

            return Optional.of(exchangeRate);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void add(Collection<ExchangeRate> newRates) {
        log.info("Adding new rates " + newRates);
        lock.writeLock().lock();
        try {
            populateRates(newRates);
            log.info("New rates successfully added");
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void reload(Collection<ExchangeRate> newRates) {
        log.info("reloading cache. new rates " + newRates);
        lock.writeLock().lock();
        try {
            cachedRates.clear();
            populateRates(newRates);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void populateRates(Collection<ExchangeRate> newRates) {
        for (ExchangeRate newRate : newRates) {
            Map<Currency, ExchangeRate> currencyMap =
                    cachedRates.computeIfAbsent(newRate.getDate(), k -> new HashMap<>());
            currencyMap.put(newRate.getCurrency(), newRate);
        }
    }
}

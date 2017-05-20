package com.company.exchange.services.cache;

import com.company.exchange.model.ExchangeRate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Currency;
import java.util.Optional;

public interface ExchangeRatesDataStorage {

    Optional<ExchangeRate> getExchangeRate(LocalDate date, Currency currency);

    void add(Collection<ExchangeRate> newRates);

    void reload(Collection<ExchangeRate> newRates);
}

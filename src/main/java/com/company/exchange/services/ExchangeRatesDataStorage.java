package com.company.exchange.services;

import com.company.exchange.model.ExchangeRate;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;

public interface ExchangeRatesDataStorage {

    Optional<ExchangeRate> getExchangeRate(LocalDate date, Currency currency);
}

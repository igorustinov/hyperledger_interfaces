package com.company.exchange.services;

import com.company.exchange.model.ExchangeRate;

import java.util.stream.Stream;

public interface ExchangeRatesDataStorage {

    Stream<ExchangeRate> getStream();
}

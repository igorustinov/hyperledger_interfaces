package com.company.exchange.model;

import java.time.LocalDate;
import java.util.Currency;

public final class ExchangeRate {

    private final Currency currency;
    private final LocalDate date;
    private final double rate;

    public ExchangeRate(Currency currency, LocalDate date, double rate) {
        this.currency = currency;
        this.date = date;
        this.rate = rate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getRate() {
        return rate;
    }
}

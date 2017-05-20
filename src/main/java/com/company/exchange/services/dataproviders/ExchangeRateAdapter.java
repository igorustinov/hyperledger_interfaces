package com.company.exchange.services.dataproviders;

import com.company.exchange.model.ExchangeRate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

class ExchangeRateAdapter {

    List<ExchangeRate> translate(Envelope envelope) {
        List<ExchangeRate> xRates = new ArrayList<>();
        for (Cube cube : envelope.getCube().getCubes()) {
            LocalDate ld = LocalDate.parse(cube.getDate());
            for (Cube rate : cube.getCubes()) {
                xRates.add(new ExchangeRate(Currency.getInstance(rate.getCurrency()), ld, Double.parseDouble(rate.getRate())));
            }
        }
        return xRates;
    }

}

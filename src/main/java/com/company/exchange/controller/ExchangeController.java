package com.company.exchange.controller;

import com.company.exchange.controller.error.IllegalExchangeArgumentException;
import com.company.exchange.controller.error.RateNotFoundException;
import com.company.exchange.model.ExchangeRate;
import com.company.exchange.services.ExchangeRatesDataStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @Autowired
    private ExchangeRatesDataStorage exchangeRatesDataStorage;

    @RequestMapping(value = "/{date}/{currency}", method = GET)
    @ResponseBody
    ExchangeRate getExchangeRate(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date,
                                 @PathVariable("currency") String currency) {

        final LocalDate reqDate = LocalDate.parse(date);
        validateDate(reqDate);
        final Currency reqCurrency = Currency.getInstance(currency.toUpperCase());

        final Optional<ExchangeRate> exchangeRate = exchangeRatesDataStorage.getStream()
                .filter(xr -> xr.getDate().equals(reqDate))
                .filter(xr -> xr.getCurrency().equals(reqCurrency))
                .findFirst();

        if (exchangeRate.isPresent()) {
            return exchangeRate.get();
        } else {
            throw new RateNotFoundException("Specified rate wasn't found");
        }
    }

    private void validateDate(LocalDate reqDate) {
        final LocalDate now = LocalDate.now();
        if (reqDate.compareTo(now) > 0) {
            throw new IllegalExchangeArgumentException("Date is in future");
        }

        final long daysBetween = ChronoUnit.DAYS.between(reqDate, now);
        if (daysBetween > 30) {
            throw new IllegalExchangeArgumentException("Requesting for older than 30 days in the past");
        }
    }
}

package com.company.exchange.controller;

import com.company.exchange.controller.error.CacheNotInitializedException;
import com.company.exchange.controller.error.IllegalExchangeArgumentException;
import com.company.exchange.controller.error.RateNotFoundException;
import com.company.exchange.model.ExchangeRate;
import com.company.exchange.services.cache.ExchangeRatesDataStorage;
import com.company.exchange.services.dataproviders.ILedgeDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/assets")
public class ExchangeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ILedgeDataProvider dataProvider;

    private DtoFactory dtoFactory = new DtoFactory();

    @RequestMapping(value = "/{asset}", method = GET)
    @ResponseBody
    DtoFactory.ExchangeRateDto getExchangeRate(@PathVariable("asset") String currency) {

        //todo implement
        throw new RuntimeException();


    }
}

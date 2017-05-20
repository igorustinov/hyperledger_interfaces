package com.company.exchange.services.cache;

import com.company.exchange.model.ExchangeRate;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ExchangeRatesDataStorageImplTest {
    @Test
    public void reload() throws Exception {
        ExchangeRatesDataStorage cache = new ExchangeRatesDataStorageImpl();
        List<ExchangeRate> rates = new ArrayList<>();
        rates.add(new ExchangeRate(Currency.getInstance("USD"), LocalDate.parse("2017-05-20"), 1.5));
        rates.add(new ExchangeRate(Currency.getInstance("EUR"), LocalDate.parse("2017-05-20"), 1.6));

        cache.reload(rates);

        final Optional<ExchangeRate> usd = cache.getExchangeRate(LocalDate.parse("2017-05-20"), Currency.getInstance("USD"));
        assertTrue(usd.isPresent());

        final Optional<ExchangeRate> eur = cache.getExchangeRate(LocalDate.parse("2017-05-20"), Currency.getInstance("EUR"));
        assertTrue(eur.isPresent());

        final Optional<ExchangeRate> gbp = cache.getExchangeRate(LocalDate.parse("2017-05-20"), Currency.getInstance("GBP"));
        assertFalse(gbp.isPresent());

    }

}
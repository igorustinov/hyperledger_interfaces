package com.company.exchange.services.jobs;

import com.company.exchange.model.ExchangeRate;
import com.company.exchange.services.cache.ExchangeRatesDataStorage;
import com.company.exchange.services.dataproviders.DataRetrievalException;
import com.company.exchange.services.dataproviders.ExchangeDataProvider;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ScheduledLoaderTest {

    @Test
    public void basicCacheReloadHappyDay() throws Exception {
        ExchangeDataProvider dataProvider = mock(ExchangeDataProvider.class);
        List<ExchangeRate> rates = new ArrayList<>();
        rates.add(new ExchangeRate(Currency.getInstance("USD"), LocalDate.parse("2017-05-20"), 10.0));
        when(dataProvider.retrieveMonthly()).thenReturn(rates);

        when(dataProvider.retrieveDaily()).thenReturn(rates);

        ScheduledLoader sl = new ScheduledLoader();
        sl.setDataProvider(dataProvider);
        sl.setCache(mock(ExchangeRatesDataStorage.class));
        sl.setRepeatInterval(5);

        sl.reloadCache();

        assertFalse(sl.isDailyRatesLoaded());
        assertTrue(sl.getMonthlyRatesAttemptCounter() == 1);
        verify(dataProvider, times(1)).retrieveMonthly();

    }

    @Test
    @Ignore
    public void loadDailyCacheWhenMonthlyUpdateFails() throws Exception {
        //dirty way to test basic fail scenario
        ExchangeDataProvider dataProvider = mock(ExchangeDataProvider.class);
        List<ExchangeRate> rates = new ArrayList<>();
        rates.add(new ExchangeRate(Currency.getInstance("USD"), LocalDate.parse("2017-05-20"), 10.0));
        when(dataProvider.retrieveMonthly())
                .thenThrow(new DataRetrievalException())
                .thenThrow(new DataRetrievalException())
                .thenReturn(rates);

        when(dataProvider.retrieveDaily()).thenReturn(rates);

        ScheduledLoader sl = new ScheduledLoader();
        sl.setDataProvider(dataProvider);
        sl.setCache(mock(ExchangeRatesDataStorage.class));
        sl.setRepeatInterval(5);

        new Thread(sl::reloadCache).start();
    }
}
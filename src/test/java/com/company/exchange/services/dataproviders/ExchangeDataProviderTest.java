package com.company.exchange.services.dataproviders;

import com.company.exchange.model.ExchangeRate;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class ExchangeDataProviderTest {
    @Test
    public void retrieveMonthly() throws Exception {
        final List<ExchangeRate> exchangeRates = new ExchangeDataProvider().retrieveMonthly();
        System.out.println();
    }

    @Test
    public void retrieveDaily() throws Exception {
        final List<ExchangeRate> exchangeRates = new ExchangeDataProvider().retrieveDaily();
        System.out.println();
    }

}
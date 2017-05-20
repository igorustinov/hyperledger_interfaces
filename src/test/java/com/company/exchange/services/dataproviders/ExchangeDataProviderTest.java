package com.company.exchange.services.dataproviders;

import com.company.exchange.model.ExchangeRate;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class ExchangeDataProviderTest {
    //basic connectivity tests; shouldn't be run in testsuite
    @Test
    public void retrieveMonthly() throws Exception {
        final List<ExchangeRate> exchangeRates = new ExchangeDataProvider().retrieveMonthly();
    }

    @Test
    public void retrieveDaily() throws Exception {
        final List<ExchangeRate> exchangeRates = new ExchangeDataProvider().retrieveDaily();
    }
}
package com.company.exchange.controller;

import com.company.exchange.model.ExchangeRate;
import com.company.exchange.services.ExchangeRatesDataStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
@SpringBootTest
public class ExchangeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExchangeRatesDataStorage exchangeCacheMock;

    private WebApplicationContext context;

    @InjectMocks
    private ExchangeController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void get_happyDay() throws Exception {
        List<ExchangeRate> rates = new ArrayList<>();
        rates.add(new ExchangeRate(Currency.getInstance("USD"), LocalDate.parse("2017-05-14"), 1.5));

        when(exchangeCacheMock.getStream()).thenReturn(rates.stream());

        mockMvc.perform(get("/exchange/2017-05-14/usd"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.rate", is(1.5)));

        verify(exchangeCacheMock, times(1)).getStream();
        verifyNoMoreInteractions(exchangeCacheMock);
    }

    @Test(expected=NestedServletException.class)
    public void get_notFoundDate() throws Exception {
        List<ExchangeRate> rates = new ArrayList<>();
        rates.add(new ExchangeRate(Currency.getInstance("USD"), LocalDate.parse("2017-05-13"), 1.5));

        when(exchangeCacheMock.getStream()).thenReturn(rates.stream());

        mockMvc.perform(get("/exchange/2017-05-14/usd"))
                .andExpect(status().isNotFound());

        verify(exchangeCacheMock, times(1)).getStream();
        verifyNoMoreInteractions(exchangeCacheMock);
    }

    @Test(expected=NestedServletException.class)
    public void get_notFoundCurrency() throws Exception {
        List<ExchangeRate> rates = new ArrayList<>();
        rates.add(new ExchangeRate(Currency.getInstance("GBP"), LocalDate.parse("2017-05-14"), 1.5));

        when(exchangeCacheMock.getStream()).thenReturn(rates.stream());

        mockMvc.perform(get("/exchange/2017-05-14/usd"))
                .andExpect(status().isNotFound());
    }
}
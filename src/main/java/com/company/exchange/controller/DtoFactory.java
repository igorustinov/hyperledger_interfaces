package com.company.exchange.controller;

import com.company.exchange.model.ExchangeRate;

import java.util.function.Function;

class DtoFactory {

    ExchangeRateDto tranlateToDto(ExchangeRate exchangeRate) {
        return getTranslateDtoFunction().apply(exchangeRate);
    }

    private Function<ExchangeRate, ExchangeRateDto> getTranslateDtoFunction() {
        return exchangeRate -> {
            ExchangeRateDto dto = new ExchangeRateDto();
            dto.setRate(String.valueOf(exchangeRate.getRate()));
            dto.setDate(exchangeRate.getDate().toString());
            dto.setCurrency(exchangeRate.getCurrency().getCurrencyCode());
            return dto;
        };
    }

    class ExchangeRateDto {
        private String rate;
        private String date;
        private String currency;

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }
}

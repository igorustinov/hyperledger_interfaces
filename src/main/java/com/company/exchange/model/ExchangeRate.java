package com.company.exchange.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Currency;

public final class ExchangeRate implements Comparable<ExchangeRate> {

    private final Currency currency;
    private final LocalDate date;
    private final double rate;

    public ExchangeRate(@NotNull Currency currency, @NotNull LocalDate date, @NotNull double rate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeRate that = (ExchangeRate) o;

        if (Double.compare(that.rate, rate) != 0) return false;
        if (!currency.equals(that.currency)) return false;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = currency.hashCode();
        result = 31 * result + date.hashCode();
        temp = Double.doubleToLongBits(rate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


    @Override
    public int compareTo(ExchangeRate that) {
        if (this == that) {
            return 0;
        }
        int comp = this.getDate().compareTo(that.getDate());
        if (comp != 0) {
            return comp;
        }

        comp = this.getCurrency().getCurrencyCode().compareTo(that.getCurrency().getCurrencyCode());
        if (comp != 0) {
            return comp;
        }

        comp = Double.compare(this.getRate(), that.getRate());
        if (comp != 0) {
            return comp;
        }

        assert this.equals(that);

        return 0;
    }
}

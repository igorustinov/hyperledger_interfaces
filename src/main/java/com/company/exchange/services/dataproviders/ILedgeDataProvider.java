package com.company.exchange.services.dataproviders;

/**
 * Created by igoru on 13-Aug-17.
 */
public interface ILedgeDataProvider {
    boolean put(String input);

    String read(String input);
}

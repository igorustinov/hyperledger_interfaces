package com.company.exchange.controller.error;

public class IllegalExchangeArgumentException extends Exception {
    public IllegalExchangeArgumentException() {
        super();
    }

    public IllegalExchangeArgumentException(String message) {
        super(message);
    }

    public IllegalExchangeArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalExchangeArgumentException(Throwable cause) {
        super(cause);
    }

    protected IllegalExchangeArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.company.exchange.controller.error;

public class RateNotFoundException extends RuntimeException {
    public RateNotFoundException() {
        super();
    }

    public RateNotFoundException(String message) {
        super(message);
    }

    public RateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RateNotFoundException(Throwable cause) {
        super(cause);
    }
}

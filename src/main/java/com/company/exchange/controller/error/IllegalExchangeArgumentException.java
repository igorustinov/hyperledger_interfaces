package com.company.exchange.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalExchangeArgumentException extends RuntimeException {
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

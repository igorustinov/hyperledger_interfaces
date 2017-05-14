package com.company.exchange.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
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

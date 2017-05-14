package com.company.exchange.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CacheNotInitializedException extends RuntimeException {
    public CacheNotInitializedException() {
        super();
    }

    public CacheNotInitializedException(String message) {
        super(message);
    }

    public CacheNotInitializedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheNotInitializedException(Throwable cause) {
        super(cause);
    }
}

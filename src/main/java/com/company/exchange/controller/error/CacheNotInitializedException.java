package com.company.exchange.controller.error;

public class CacheNotInitializedException extends Exception {
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

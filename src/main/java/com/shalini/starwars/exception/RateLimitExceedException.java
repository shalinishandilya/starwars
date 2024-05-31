package com.shalini.starwars.exception;

public class RateLimitExceedException extends Exception {
    public RateLimitExceedException(String message) {
        super(message);
    }
}

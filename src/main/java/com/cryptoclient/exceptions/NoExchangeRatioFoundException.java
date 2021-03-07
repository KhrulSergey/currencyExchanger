package com.cryptoclient.exceptions;

public class NoExchangeRatioFoundException extends RuntimeException {
    public NoExchangeRatioFoundException(String message) {
        super(message);
    }
}

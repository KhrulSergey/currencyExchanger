package com.cryptoclient.exceptions;

public class ExchangeRatioAlreadyTakenException extends RuntimeException {
    public ExchangeRatioAlreadyTakenException(String message) {
        super(message);
    }
}

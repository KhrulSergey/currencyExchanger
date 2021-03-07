package com.cryptoclient.exceptions;

public class ExchangeRatioPriceTooOldException extends RuntimeException {
    public ExchangeRatioPriceTooOldException(String message) {
        super(message);
    }
}

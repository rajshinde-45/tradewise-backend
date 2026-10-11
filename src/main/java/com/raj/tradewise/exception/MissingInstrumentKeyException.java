package com.raj.tradewise.exception;

public class MissingInstrumentKeyException extends RuntimeException {
    public MissingInstrumentKeyException(String message) {
        super(message);
    }
}

package com.jpmorgan.exceptions;

public class IncorrectTradeDataException extends RuntimeException {
    public IncorrectTradeDataException(String message) {
        super(message);
    }
}

package com.jpmorgan.exceptions;

public class IncorrectStockDataException extends RuntimeException {

    public IncorrectStockDataException(String message) {
        super(message);
    }
}

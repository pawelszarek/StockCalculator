package com.jpmorgan.exceptions;

public class UnsupportedStockTypeException extends RuntimeException {

    public UnsupportedStockTypeException(String message) {
        super(message);
    }
}

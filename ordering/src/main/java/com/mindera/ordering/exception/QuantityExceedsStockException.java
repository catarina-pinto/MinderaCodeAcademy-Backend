package com.mindera.ordering.exception;

public class QuantityExceedsStockException extends RuntimeException {
    public QuantityExceedsStockException(String message) {
        super(message);
    }

    public QuantityExceedsStockException(String message, Throwable cause) {
        super(message, cause);
    }
}

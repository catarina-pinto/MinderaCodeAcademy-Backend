package com.mindera.discount.exception;

public class DiscountNotFoundException extends RuntimeException {
    public DiscountNotFoundException(String message) {
        super(message);
    }

    public DiscountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

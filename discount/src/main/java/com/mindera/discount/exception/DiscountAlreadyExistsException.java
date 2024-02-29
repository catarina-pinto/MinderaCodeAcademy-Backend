package com.mindera.discount.exception;

public class DiscountAlreadyExistsException extends RuntimeException {
    public DiscountAlreadyExistsException(String message) {
        super(message);
    }

    public DiscountAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

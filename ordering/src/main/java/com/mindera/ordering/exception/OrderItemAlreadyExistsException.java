package com.mindera.ordering.exception;

public class OrderItemAlreadyExistsException extends RuntimeException {
    public OrderItemAlreadyExistsException(String message) {
        super(message);
    }

    public OrderItemAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.mindera.payment.exception;

public class TransactionAlreadyExistsException extends RuntimeException {
    public TransactionAlreadyExistsException(String message) {
        super(message);
    }

    public TransactionAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

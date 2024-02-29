package com.mindera.wallet.exception;

public class WithdrawalExceedsAvailableAmountException extends RuntimeException {
    public WithdrawalExceedsAvailableAmountException(String message) {
        super(message);
    }

    public WithdrawalExceedsAvailableAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}

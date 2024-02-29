package com.mindera.wallet.controller;

import com.mindera.wallet.domain.Transaction;
import com.mindera.wallet.exception.TransactionAlreadyExistsException;
import com.mindera.wallet.exception.TransactionNotFoundException;
import com.mindera.wallet.exception.WithdrawalExceedsAvailableAmountException;
import com.mindera.wallet.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mindera.wallet.model.Error;

@RestController
@RequestMapping("/withdrawal")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Transaction> doTransaction(@RequestParam Integer walletId, @RequestBody Transaction withdrawalRequest) {
        Transaction toAdd = service.doTransaction(walletId, withdrawalRequest);
        return ResponseEntity.status(HttpStatus.OK).body(toAdd);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleTransactionNotFound(TransactionNotFoundException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TransactionAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleTransactionAlreadyExists(TransactionAlreadyExistsException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.CONFLICT.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(WithdrawalExceedsAvailableAmountException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleWithdrawalExceedsAvailableAmount(WithdrawalExceedsAvailableAmountException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}

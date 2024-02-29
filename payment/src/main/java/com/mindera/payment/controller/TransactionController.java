package com.mindera.payment.controller;

import com.mindera.payment.domain.Transaction;
import com.mindera.payment.exception.TransactionAlreadyExistsException;
import com.mindera.payment.exception.TransactionNotFoundException;
import com.mindera.payment.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mindera.payment.model.Error;

@RestController
@RequestMapping("/payment")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Transaction> doTransaction(@RequestBody Transaction paymentRequest, @RequestParam Integer orderId) {
        Transaction toAdd = service.doTransaction(paymentRequest, orderId);
        return ResponseEntity.status(HttpStatus.OK).body(toAdd);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Transaction> getTransactionByOrderId(@PathVariable Integer orderId) {
        return ResponseEntity.ok(service.getTransactionByOrderId(orderId));
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
}

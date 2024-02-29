package com.mindera.wallet.controller;

import com.mindera.wallet.domain.Transaction;
import com.mindera.wallet.exception.WalletAlreadyExistsException;
import com.mindera.wallet.exception.WalletNotFoundException;
import com.mindera.wallet.domain.Wallet;
import com.mindera.wallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mindera.wallet.model.Error;

import java.util.Set;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Wallet> getWallet(@RequestParam Integer sellerId) {
        return ResponseEntity.ok(service.getOne(sellerId));
    }

    @GetMapping("/transactions")
    public ResponseEntity<Set<Transaction>> getWalletTransactions(@RequestParam Integer sellerId) {
        return ResponseEntity.ok(service.getWalletTransactions(sellerId));
    }

    @PostMapping
    public ResponseEntity<Wallet> addWallet(@RequestBody Wallet wallet, @RequestParam Integer sellerId) {
        Wallet toAdd = service.addOne(wallet, sellerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(toAdd);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> partiallyUpdateWallet(@PathVariable Integer id, @RequestBody Wallet wallet) {
        service.partiallyUpdateWallet(id, wallet);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Integer id) {
        service.deleteWallet(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleWalletNotFound(WalletNotFoundException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(WalletAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleWalletAlreadyExists(WalletAlreadyExistsException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.CONFLICT.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}

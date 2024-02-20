package com.mindera.wallet.service;

import com.mindera.wallet.exception.WalletAlreadyExistsException;
import com.mindera.wallet.exception.WalletNotFoundException;
import com.mindera.wallet.model.Wallet;
import com.mindera.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository repository;

    private void validateWalletNotFound(Optional<Wallet> wallet, Integer id, String x) {
        if (wallet.isEmpty()) {
            throw new WalletNotFoundException("Wallet " + id + x);
        }
    }
    public Wallet getOne(Integer id, Integer sellerId) {
        Optional<Wallet> wallet = repository.findWalletBySeller(sellerId);
        validateWalletNotFound(wallet, id, " not found");
        return wallet.get();
    }

    public Wallet addOne(Wallet wallet) {
        try {
            repository.save(wallet);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("duplicate key")) {
                throw new WalletAlreadyExistsException("Wallet already exists!", e);
            }
        }
        return wallet;
    }

    public void partiallyUpdateWallet(Integer id, Wallet toUpdate) {
        Optional<Wallet> wallet = repository.findById(id);
        validateWalletNotFound(repository.findById(id), id, " not found!");

        if (!isNull(toUpdate.getTotalAmount())) {
            wallet.get().setTotalAmount(toUpdate.getTotalAmount());
        }

        repository.save(wallet.get());
    }

    public void deleteWallet(Integer id) {
        Optional<Wallet> wallet = repository.findById(id);
        validateWalletNotFound(wallet, id, " not found!");
        repository.delete(wallet.get());
    }
}

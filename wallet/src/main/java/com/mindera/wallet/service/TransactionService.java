package com.mindera.wallet.service;

import com.mindera.wallet.domain.Transaction;
import com.mindera.wallet.domain.Wallet;
import com.mindera.wallet.exception.TransactionAlreadyExistsException;
import com.mindera.wallet.exception.TransactionNotFoundException;
import com.mindera.wallet.exception.WalletNotFoundException;
import com.mindera.wallet.exception.WithdrawalExceedsAvailableAmountException;
import com.mindera.wallet.repository.TransactionRepository;
import com.mindera.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final WalletRepository walletRepository;

    public Transaction doTransaction(Integer walletId, Transaction paymentRequest) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isEmpty()) {
            throw new WalletNotFoundException("Wallet " + walletId + " not found");
        }

        if (paymentRequest.getAmount() <= wallet.get().getTotalAmount()) {
            try {
                paymentRequest.setWallet(wallet.get());
                repository.save(paymentRequest);
            } catch (DataIntegrityViolationException e) {
                if (e.getMessage().contains("duplicate key")) {
                    throw new TransactionAlreadyExistsException("Transaction already exists!", e);
                }
            }

            Float beforeTransactionTotalAmount = wallet.get().getTotalAmount();
            wallet.get().setTotalAmount(beforeTransactionTotalAmount - paymentRequest.getAmount());
            walletRepository.save(wallet.get());

            return paymentRequest;
        }

        throw new WithdrawalExceedsAvailableAmountException("The requested value exceeds the available one.");
    }
}

package com.mindera.wallet.repository;

import com.mindera.wallet.domain.Transaction;
import com.mindera.wallet.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}

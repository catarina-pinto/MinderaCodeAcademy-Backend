package com.mindera.payment.repository;

import com.mindera.payment.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findByOrderId(Integer orderId);
}

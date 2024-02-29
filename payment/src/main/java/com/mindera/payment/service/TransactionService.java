package com.mindera.payment.service;

import com.mindera.payment.domain.Transaction;
import com.mindera.payment.exception.TransactionAlreadyExistsException;
import com.mindera.payment.exception.TransactionNotFoundException;
import com.mindera.payment.model.OrderDTO;
import com.mindera.payment.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final RestTemplate restTemplate;

    public Transaction doTransaction(Transaction paymentRequest, Integer orderId) {
        try {
            String url = "http://app-orders:8080/order/".concat(orderId.toString());
            OrderDTO order = restTemplate.getForObject(url, OrderDTO.class);

            paymentRequest.setOrderId(order.getId());
            paymentRequest.setAmount(order.getTotalValue());
            repository.save(paymentRequest);

            updateOrderStatus(orderId);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("duplicate key")) {
                throw new TransactionAlreadyExistsException("Transaction already exists!", e);
            }
        }

        return paymentRequest;
    }

    public Transaction getTransactionByOrderId(Integer orderId) {
        Optional<Transaction> transaction = repository.findByOrderId(orderId);
        if (transaction.isEmpty()) {
            throw new TransactionNotFoundException("Transaction not found for order " + orderId);
        }

        return transaction.get();
    }

    public void updateOrderStatus(Integer orderId) {
        String url = "http://app-orders:8080/order/".concat(orderId.toString());

        OrderDTO order = OrderDTO.builder()
                .status("OPENED")
                .build();

        restTemplate.patchForObject(url, order, OrderDTO.class);
    }
}

package com.mindera.ordering.service;

import com.mindera.ordering.domain.OrderItem;
import com.mindera.ordering.exception.OrderItemAlreadyExistsException;
import com.mindera.ordering.exception.OrderItemNotFoundException;
import com.mindera.ordering.exception.QuantityExceedsStockException;
import com.mindera.ordering.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    public final OrderItemRepository repository;

    private void validateOrderItemNotFound(Optional<OrderItem> orderItem, Integer id, String x) {
        if (orderItem.isEmpty()) {
            throw new OrderItemNotFoundException("Order " + id + x);
        }
    }
    public OrderItem getOne(Integer id) {
        Optional<OrderItem> orderItem = repository.findById(id);
        validateOrderItemNotFound(orderItem, id, " not found");
        return orderItem.get();
    }

    public List<OrderItem> getAll() {
        return repository.findAll();
    }

    public OrderItem addOne(OrderItem orderItem) {
        try {
            repository.save(orderItem);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("duplicate key")) {
                throw new OrderItemAlreadyExistsException("The order already exists!");
            }
        }
        return orderItem;
    }

    public void updateOrderItem(Integer id, OrderItem orderItem) {
        validateOrderItemNotFound(repository.findById(id), id, " not found!");
        orderItem.setId(id);
        repository.save(orderItem);
    }

    public void partiallyUpdateOrderItem(Integer id, OrderItem toUpdate) {
        Optional<OrderItem> order = repository.findById(id);
        validateOrderItemNotFound(repository.findById(id), id, " not found!");

        if (toUpdate.getQuantity() > toUpdate.getMaxQuantity()) {
            throw new QuantityExceedsStockException("Required quantity exceeds available one.");
        }

        if (!isNull(toUpdate.getQuantity())) {
            order.get().setQuantity(toUpdate.getQuantity());
        }

        repository.save(order.get());
    }

    public void deleteOrderItem(Integer id) {
        Optional<OrderItem> orderItem = repository.findById(id);
        validateOrderItemNotFound(orderItem, id, " not found!");
        repository.delete(orderItem.get());
    }
}

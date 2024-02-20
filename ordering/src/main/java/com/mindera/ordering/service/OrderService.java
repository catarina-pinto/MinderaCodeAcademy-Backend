package com.mindera.ordering.service;

import com.mindera.ordering.domain.Order;
import com.mindera.ordering.domain.OrderItem;
import com.mindera.ordering.model.ProductDTO;
import com.mindera.ordering.repository.OrderRepository;
import com.mindera.ordering.exception.OrderAlreadyExistsException;
import com.mindera.ordering.exception.OrderNotFoundException;
// import com.mindera.ordering.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final RestTemplate restTemplate;

    private void validateOrderNotFound(Optional<Order> order, Integer id, String x) {
        if (order.isEmpty()) {
            throw new OrderNotFoundException("Order " + id + x);
        }
    }
    public Order getOne(Integer id) {
        Optional<Order> order = repository.findById(id);
        validateOrderNotFound(order, id, " not found");
        return order.get();
    }

    public List<Order> getAll(Integer userId) {
        if (isNull(userId)) {
            return repository.findAll();
        }

        return repository.findOrdersByUserId(userId);
    }

    public Order addOne(Order order) {
        try {
            repository.save(order);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("duplicate key")) {
                throw new OrderAlreadyExistsException("The order already exists!");
            }
        }
        return order;
    }

    public void updateOrder(Integer id, Order order) {
        validateOrderNotFound(repository.findById(id), id, " not found!");
        order.setId(id);
        repository.save(order);
    }

    public void partiallyUpdateOrder(Integer id, Order toUpdate) {
        Optional<Order> order = repository.findById(id);
        validateOrderNotFound(repository.findById(id), id, " not found!");

        if (!isNull(toUpdate.getItems())) {
            order.get().setItems(toUpdate.getItems());
        }

        if (!isNull(toUpdate.getStatus())) {
            order.get().setStatus(toUpdate.getStatus());
        }

        if (!isNull(toUpdate.getTotalValue())) {
            order.get().setTotalValue(toUpdate.getTotalValue());
        }

        repository.save(order.get());
    }

    public void deleteOrder(Integer id) {
        Optional<Order> order = repository.findById(id);
        validateOrderNotFound(order, id, " not found!");
        repository.delete(order.get());
    }

    public void addProductToCart(Integer userId, Integer productId, Integer quantity) {
        Optional<Order> userOrder = repository.findOrderByUserId(userId);
        validateOrderNotFound(userOrder, userOrder.get().getId(), " not found");

        String url = "localhost:8081/product/".concat(productId.toString());
        ProductDTO product = restTemplate.getForObject(url, ProductDTO.class);

        OrderItem toAdd = new OrderItem().builder()
                .productId(productId)
                .order(userOrder.get())
                .quantity(quantity)
                .build();
    }
}

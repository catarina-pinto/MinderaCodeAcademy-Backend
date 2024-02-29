package com.mindera.ordering.controller;

import com.mindera.ordering.domain.Order;
import com.mindera.ordering.domain.OrderItem;
import com.mindera.ordering.exception.*;
import com.mindera.ordering.model.Error;
import com.mindera.ordering.service.OrderItemService;
import com.mindera.ordering.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-item")
public class OrderItemController {
    private final OrderItemService service;

    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PostMapping
    public ResponseEntity<OrderItem> addOrderItem(@RequestBody OrderItem orderItem) {
        OrderItem toAdd = service.addOne(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(toAdd);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrderItem(@PathVariable Integer id, @RequestBody OrderItem orderItem) {
        service.updateOrderItem(id, orderItem);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> partiallyUpdateOrderItem(@PathVariable Integer id, @RequestBody OrderItem orderItem) {
        service.partiallyUpdateOrderItem(id, orderItem);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Integer id) {
        service.deleteOrderItem(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleOrderItemNotFound(OrderItemNotFoundException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(OrderItemAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleOrderItemAlreadyExists(OrderItemAlreadyExistsException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.CONFLICT.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(QuantityExceedsStockException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleQuantityExceedsStock(QuantityExceedsStockException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}

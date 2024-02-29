package com.mindera.discount.controller;

import com.mindera.discount.domain.Discount;
import com.mindera.discount.exception.DiscountAlreadyExistsException;
import com.mindera.discount.exception.DiscountNotFoundException;
import com.mindera.discount.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mindera.discount.model.Error;

import java.util.List;

@RestController
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountService service;

    public DiscountController(DiscountService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts(@RequestParam(required = false) Integer productId, @RequestParam(required = false) Integer categoryId) {
        return ResponseEntity.ok(service.getAll(productId, categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscount(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PostMapping
    public ResponseEntity<Discount> addDiscount(@RequestBody Discount discount) {
        Discount toAdd = service.addOne(discount);
        return ResponseEntity.status(HttpStatus.CREATED).body(toAdd);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDiscount(@PathVariable Integer id, @RequestBody Discount discount) {
        service.updateDiscount(id, discount);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> partiallyUpdateDiscount(@PathVariable Integer id, @RequestBody Discount discount) {
        service.partiallyUpdateDiscount(id, discount);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Integer id) {
        service.deleteDiscount(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler(DiscountNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleProductNotFound(DiscountNotFoundException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DiscountAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleProductAlreadyExists(DiscountAlreadyExistsException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.CONFLICT.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}

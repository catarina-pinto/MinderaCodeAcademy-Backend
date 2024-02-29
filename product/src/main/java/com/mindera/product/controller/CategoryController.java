package com.mindera.product.controller;

import com.mindera.product.exception.CategoryAlreadyExistsException;
import com.mindera.product.exception.CategoryNotFoundException;
import com.mindera.product.domain.Category;
import com.mindera.product.model.Error;
import com.mindera.product.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category toAdd = service.addOne(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(toAdd);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        service.updateCategory(id, category);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> partiallyUpdateCategory(@PathVariable Integer id, @RequestBody Category category) {
        service.partiallyUpdateCategory(id, category);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/disable/{id}")
    public ResponseEntity<Void> disableCategory(@PathVariable Integer id) {
        service.disableCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleCategoryNotFound(CategoryNotFoundException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleCategoryAlreadyExists(CategoryAlreadyExistsException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.CONFLICT.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}

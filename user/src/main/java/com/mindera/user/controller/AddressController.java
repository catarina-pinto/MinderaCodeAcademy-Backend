package com.mindera.user.controller;

import com.mindera.user.domain.Address;
import com.mindera.user.exception.AddressAlreadyExistsException;
import com.mindera.user.exception.AddressNotFoundException;
import com.mindera.user.model.Error;
import com.mindera.user.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PostMapping
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        Address toAdd = service.addOne(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(toAdd);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        service.updateAddress(id, address);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> partiallyUpdateAddress(@PathVariable Integer id, @RequestBody Address address) {
        service.partiallyUpdateAddress(id, address);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        service.deleteAddress(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleAddressNotFound(AddressNotFoundException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AddressAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleAddressAlreadyExixts(AddressAlreadyExistsException ex) {
        Error error = new Error();
        error.setErrorCode(HttpStatus.CONFLICT.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}

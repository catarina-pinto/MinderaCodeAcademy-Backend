package com.mindera.user.service;


import com.mindera.user.exception.AddressNotFoundException;
import com.mindera.user.exception.AddressAlreadyExistsException;
import com.mindera.user.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.mindera.user.domain.Address;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;

    private void validateUserNotFound(Optional<Address> address, Integer id, String x) {
        if (address.isEmpty()) {
            throw new AddressNotFoundException("Address " + id + x);
        }
    }
    public Address getOne(Integer id) {
        Optional<Address> address = repository.findById(id);
        validateUserNotFound(address, id, " not found");
        return address.get();
    }

    public List<Address> getAll() {
        return repository.findAll();
    }

    public Address addOne(Address address) {
        try {
            repository.save(address);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("duplicate key")) {
                throw new AddressAlreadyExistsException("Address already exists!", e);
            }
        }
        return address;
    }

    public void updateAddress(Integer id, Address address) {
        validateUserNotFound(repository.findById(id), id, " not found!");
        address.setId(id);
        repository.save(address);
    }

    public void partiallyUpdateAddress(Integer id, Address toUpdate) {
        Optional<Address> address = repository.findById(id);
        validateUserNotFound(repository.findById(id), id, " not found!");

        if (!isNull(toUpdate.getCountry())) {
            address.get().setCountry(toUpdate.getCountry());
        }

        if (!isNull(toUpdate.getCity())) {
            address.get().setCity(toUpdate.getCity());
        }

        if (!isNull(toUpdate.getStreet())) {
            address.get().setStreet(toUpdate.getStreet());
        }

        if (!isNull(toUpdate.getPostalCode())) {
            address.get().setPostalCode(toUpdate.getPostalCode());
        }

        if (!isNull(toUpdate.getDoorNumber())) {
            address.get().setDoorNumber(toUpdate.getDoorNumber());
        }

        if (!isNull(toUpdate.getFiscalNumber())) {
            address.get().setFiscalNumber(toUpdate.getFiscalNumber());
        }

        if (!isNull(toUpdate.getIsFiscalAddress())) {
            address.get().setIsFiscalAddress(toUpdate.getIsFiscalAddress());
        }

        repository.save(address.get());
    }

    public void deleteAddress(Integer id) {
        Optional<Address> address = repository.findById(id);
        validateUserNotFound(address, id, " not found!");
        repository.delete(address.get());
    }
}

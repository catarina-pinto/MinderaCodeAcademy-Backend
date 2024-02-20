package com.mindera.product.service;

import com.mindera.product.exception.ProductAlreadyExistsException;
import com.mindera.product.exception.ProductNotFoundException;
import com.mindera.product.domain.Product;
import com.mindera.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    private void validateProductNotFound(Optional<Product> product, Integer id, String x) {
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product " + id + x);
        }
    }
    public Product getOne(Integer id) {
        Optional<Product> product = repository.findById(id);
        validateProductNotFound(product, id, " not found");
        return product.get();
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product addOne(Product product) {
        try {
            repository.save(product);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("duplicate key")) {
                throw new ProductAlreadyExistsException("The product already exists!");
            }
        }
        return product;
    }

    public void updateProduct(Integer id, Product product) {
        validateProductNotFound(repository.findById(id), id, " not found!");
        product.setId(id);
        repository.save(product);
    }

    public void partiallyUpdateProduct(Integer id, Product toUpdate) {
        Optional<Product> product = repository.findById(id);
        validateProductNotFound(repository.findById(id), id, " not found!");

        if (!isNull(toUpdate.getName())) {
            product.get().setName(toUpdate.getName());
        }

        if (!isNull(toUpdate.getDescription())) {
            product.get().setDescription(toUpdate.getDescription());
        }

        if (!isNull(toUpdate.getCategory())) {
            product.get().setCategory(toUpdate.getCategory());
        }

        if (!isNull(toUpdate.getPrice())) {
            product.get().setPrice(toUpdate.getPrice());
        }

        if (!isNull(toUpdate.getPromotion())) {
            product.get().setPromotion(toUpdate.getPromotion());
        }

        if (!isNull(toUpdate.getSellerId())) {
            product.get().setSellerId(toUpdate.getSellerId());
        }

        if (!isNull(toUpdate.getStock())) {
            product.get().setStock(toUpdate.getStock());
        }

        if (!isNull(toUpdate.getVat())) {
            product.get().setVat(toUpdate.getVat());
        }

        repository.save(product.get());
    }

    public void deleteProduct(Integer id) {
        Optional<Product> product = repository.findById(id);
        validateProductNotFound(product, id, " not found!");
        repository.delete(product.get());
    }
}

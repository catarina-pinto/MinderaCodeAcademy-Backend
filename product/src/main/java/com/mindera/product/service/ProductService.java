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

    public List<Product> getAll(String name, Integer sellerId, Integer categoryId, Float price) {
        if (isNull(name) && isNull(sellerId) && isNull(categoryId) && isNull(price)) {
            return repository.findAll();
        }

        return repository.findByNameAndSellerIdAndCategoryIdAndPrice(name, sellerId, categoryId, price);
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
        System.out.println(toUpdate.getStock() + "-----------");
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

        if (!isNull(toUpdate.getBasePrice())) {
            product.get().setBasePrice(toUpdate.getBasePrice());
            product.get().setFinalPrice();
        }

        if (!isNull(toUpdate.getSellerId())) {
            product.get().setSellerId(toUpdate.getSellerId());
        }

        if (!isNull(toUpdate.getStock())) {
            System.out.println("entra aqui");
            product.get().setStock(toUpdate.getStock());
            System.out.println(product.get().getStock() + "........" + toUpdate.getStock());
        }

        if (!isNull(toUpdate.getVat())) {
            product.get().setVat(toUpdate.getVat());
        }

        if (!isNull(toUpdate.getDiscountIsActive())) {
            product.get().setDiscountIsActive(toUpdate.getDiscountIsActive());
            product.get().setFinalPrice();
        }

        if (!isNull(toUpdate.getDiscount())) {
            product.get().setDiscount(toUpdate.getDiscount());
            product.get().setFinalPrice();
        }

        repository.save(product.get());
        System.out.println("Chega até aqui ?");
    }

    public void deleteProduct(Integer id) {
        Optional<Product> product = repository.findById(id);
        validateProductNotFound(product, id, " not found!");
        repository.delete(product.get());
    }
}

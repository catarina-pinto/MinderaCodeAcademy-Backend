package com.mindera.discount.service;

import com.mindera.discount.domain.Discount;
import com.mindera.discount.exception.DiscountAlreadyExistsException;
import com.mindera.discount.exception.DiscountNotFoundException;
import com.mindera.discount.model.ProductDTO;
import com.mindera.discount.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository repository;
    private final RestTemplate restTemplate;

    private void validateDiscountNotFound(Optional<Discount> product, Integer id, String x) {
        if (product.isEmpty()) {
            throw new DiscountNotFoundException("Discount " + id + x);
        }
    }
    public Discount getOne(Integer id) {
        Optional<Discount> discount = repository.findById(id);
        validateDiscountNotFound(discount, id, " not found");
        return discount.get();
    }

    public List<Discount> getAll(Integer productId, Integer categoryId) {
        if (isNull(productId) && isNull(categoryId)) {
            return repository.findAll();
        }

        return repository.findByProductIdAndCategoryId(productId, categoryId);
    }

    public Discount addOne(Discount discount) {
        try {
            repository.save(discount);
            addDiscountToProducts(discount);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("duplicate key")) {
                throw new DiscountAlreadyExistsException("The discount already exists!");
            }
        }
        return discount;
    }

    public void updateDiscount(Integer id, Discount discount) {
        validateDiscountNotFound(repository.findById(id), id, " not found!");
        discount.setId(id);
        repository.save(discount);
        addDiscountToProducts(discount);
    }

    public void partiallyUpdateDiscount(Integer id, Discount toUpdate) {
        Optional<Discount> discount = repository.findById(id);
        validateDiscountNotFound(repository.findById(id), id, " not found!");

        if (!isNull(toUpdate.getPercentage())) {
            discount.get().setPercentage(toUpdate.getPercentage());
        }

        repository.save(discount.get());
        addDiscountToProducts(discount.get());
    }

    public void deleteDiscount(Integer id) {
        Optional<Discount> discount = repository.findById(id);
        validateDiscountNotFound(discount, id, " not found!");
        repository.delete(discount.get());
    }

    public void addDiscountToProducts(Discount discount) {
        if (!isNull(discount.getProductId())) {
            updateProductDiscount(discount.getProductId(), discount.getPercentage());
        }

        if (!isNull(discount.getCategoryId())) {
            String url = "http://app-products:8080/product?categoryId=".concat(discount.getCategoryId().toString());

            ArrayList<ProductDTO> categoryProducts = restTemplate.getForObject(url, ArrayList.class);

            for (ProductDTO product : categoryProducts) {
                updateProductDiscount(product.getId(), discount.getPercentage());
            }
        }

        if (!isNull(discount.getApplyToAll())) {
            if (discount.getApplyToAll()) {
                String url = "http://app-products:8080/product";

                ArrayList<ProductDTO> allProducts = restTemplate.getForObject(url, ArrayList.class);

                for (ProductDTO product : allProducts) {
                    updateProductDiscount(product.getId(), discount.getPercentage());
                }
            }
        }
    }

    private void updateProductDiscount(Integer productId, Float discount) {
        String url = "http://app-products:8080/product/".concat(productId.toString());

        ProductDTO product = ProductDTO.builder()
                .discount(discount)
                .build();

        restTemplate.patchForObject(url, product, ProductDTO.class);
    }
}

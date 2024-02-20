package com.mindera.product.service;

import com.mindera.product.exception.CategoryAlreadyExistsException;
import com.mindera.product.exception.CategoryNotFoundException;
import com.mindera.product.domain.Category;
import com.mindera.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    private void validateCategoryNotFound(Optional<Category> category, Integer id, String x) {
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("Category " + id + x);
        }
    }
    public Category getOne(Integer id) {
        Optional<Category> category = repository.findById(id);
        validateCategoryNotFound(category, id, " not found");
        return category.get();
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Category addOne(Category category) {
        try {
            repository.save(category);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("duplicate key")) {
                throw new CategoryAlreadyExistsException("The category already exists!");
            }
        }
        return category;
    }

    public void updateCategory(Integer id, Category category) {
        validateCategoryNotFound(repository.findById(id), id, " not found!");
        category.setId(id);
        repository.save(category);
    }

    public void partiallyUpdateCategory(Integer id, Category toUpdate) {
        Optional<Category> category = repository.findById(id);
        validateCategoryNotFound(repository.findById(id), id, " not found!");

        if (!isNull(toUpdate.getName())) {
            category.get().setName(toUpdate.getName());
        }

        if (!isNull(toUpdate.getDescription())) {
            category.get().setDescription(toUpdate.getDescription());
        }

        if (!isNull((toUpdate.getIsDisabled())) & !toUpdate.getIsDisabled().equals(category.get().getIsDisabled())) {
            category.get().setIsDisabled(toUpdate.getIsDisabled());
        }

        repository.save(category.get());
    }

    //public void deleteCategory(Integer id) {
    //    Optional<Category> category = repository.findById(id);
    //    validateCategoryNotFound(category, id, " not found!");
    //    repository.delete(category.get());
    //}
}

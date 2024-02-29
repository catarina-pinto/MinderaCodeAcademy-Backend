package com.mindera.product.repository;

import com.mindera.product.domain.Category;
import com.mindera.product.domain.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT P FROM Product P JOIN FETCH P.category C WHERE (P.name = :name OR :name is null) AND (P.sellerId = :sellerId OR :sellerId is null) AND (C.id = :categoryId OR :categoryId is null) AND (P.finalPrice <= :finalPrice OR :finalPrice is null)")
    List<Product> findByNameAndSellerIdAndCategoryIdAndPrice(@Param("name") String name, @Param("sellerId") Integer sellerId, @Param("categoryId") Integer categoryId, @Param("finalPrice") Float finalPrice);
}

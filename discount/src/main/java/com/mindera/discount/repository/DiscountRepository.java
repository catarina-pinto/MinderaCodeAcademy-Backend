package com.mindera.discount.repository;

import com.mindera.discount.domain.Discount;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    @Query("SELECT D FROM Discount D WHERE (D.productId = :productId OR :productId is null) AND (D.categoryId = :categoryId OR :categoryId is null)")
    List<Discount> findByProductIdAndCategoryId(@Param("productId") Integer productId, @Param("categoryId") Integer categoryId);
}

package com.mindera.ordering.repository;

import com.mindera.ordering.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByUserId(Integer userId);
    Optional<Order> findOrderByUserId(Integer userId);
}

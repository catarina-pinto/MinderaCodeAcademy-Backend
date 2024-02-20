package com.mindera.ordering.domain;

import com.mindera.ordering.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer userId;
    @OneToMany(mappedBy = "order")
    private Set<OrderItem> items;
    @Column
    private Float totalValue;
    @Column
    private Status status;
}

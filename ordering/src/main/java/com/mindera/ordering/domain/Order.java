package com.mindera.ordering.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private Set<OrderItem> items;
    @Column
    private Float totalValue;
    @Column
    private Status status;
}

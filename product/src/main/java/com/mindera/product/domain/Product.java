package com.mindera.product.domain;

import com.mindera.product.domain.Category;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Float price;
    @Column
    private Float promotion;
    @Column
    private Integer sellerId;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column
    private Float vat;
    @Column
    private Integer stock;
}

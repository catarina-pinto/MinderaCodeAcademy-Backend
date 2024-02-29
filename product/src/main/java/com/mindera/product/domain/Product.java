package com.mindera.product.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Float basePrice;
    @Column
    private Float finalPrice;
    @Column
    private Float discount;
    @Column
    private Boolean discountIsActive;
    @Column
    private Integer sellerId;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;
    @Column
    private Float vat;
    @Column
    private Integer stock;

    public void setFinalPrice() {
        if (this.discountIsActive) {
            this.finalPrice = basePrice - basePrice * this.discount;
        } else {
            this.finalPrice = basePrice;
        }
    }
}

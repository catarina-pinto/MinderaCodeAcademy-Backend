package com.mindera.discount.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.DecimalMax;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "1.0")
    private Float percentage;
    @Column
    private Integer productId;
    @Column
    private Integer categoryId;
    @Column
    private Boolean applyToAll;
}

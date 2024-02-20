package com.mindera.ordering.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Float price;
    private Float promotion;
    private Integer sellerId;
    private CategoryDTO category;
    private Float vat;
    private Integer stock;
}

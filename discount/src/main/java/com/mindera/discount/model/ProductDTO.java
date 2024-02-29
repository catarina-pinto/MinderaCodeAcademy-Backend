package com.mindera.discount.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDTO {
    private Integer id;
    private Float discount;
    private CategoryDTO category;
}

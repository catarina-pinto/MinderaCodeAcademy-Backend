package com.mindera.discount.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO {
    private Integer id;
    private Set<ProductDTO> categoryProducts;
}

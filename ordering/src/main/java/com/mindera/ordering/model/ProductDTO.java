package com.mindera.ordering.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDTO {
    private Integer id;
    private Float finalPrice;
    private Integer stock;
}

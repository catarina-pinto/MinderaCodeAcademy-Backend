package com.mindera.payment.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDTO {
    private Integer id;
    private String status;
    private Float totalValue;
}

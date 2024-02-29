package com.mindera.ordering.model;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO {
    private Integer id;
    private String name;
    private String description;
    private Boolean isDisabled;
}

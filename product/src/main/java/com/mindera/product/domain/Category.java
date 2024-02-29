package com.mindera.product.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column(columnDefinition = "boolean default false")
    private Boolean isDisabled;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private Set<Product> categoryProducts;
}

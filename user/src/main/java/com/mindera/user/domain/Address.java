package com.mindera.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String country;
    @Column
    private String city;
    @Column
    private String street;
    @Column
    private String doorNumber;
    @Column
    private String postalCode;
    @Column
    private String fiscalNumber;
    @Column
    private Boolean isFiscalAddress;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

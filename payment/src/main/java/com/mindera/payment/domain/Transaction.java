package com.mindera.payment.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private Integer orderId;
    @Column
    private String paymentMode;
    @Column
    private String referenceNumber;
    @Column
    private Instant paymentDate;
    @Column
    private String paymentStatus;
    @Column
    private Float amount;
}

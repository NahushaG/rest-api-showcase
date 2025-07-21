package com.example.payout.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name ="payout")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Payout {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String reference;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING) //Stores enum as String in DB
    @Column(nullable = false)
    private PayoutStatus status;
}

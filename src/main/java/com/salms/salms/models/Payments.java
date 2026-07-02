package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbl_payments")
public class Payments implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "appointment_id", referencedColumnName = "id", nullable = false)
    private Appointments appointments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customers_id", referencedColumnName = "id", nullable = false)
    private Customers customers;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private Instant createdOn;

    @Column(nullable = false)
    private Instant updatedOn;

    public enum PaymentStatus {
        PENDING,
        PAID,
        CANCELLED
    }
}

package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "tbl_appointments")
public class Appointments implements Serializable {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private BigDecimal staffName;

    @Column(nullable = false)
    private Instant appDate;

    @Column(nullable = false)
    private Instant time;

    @Column(nullable = false)
    private String servicetype;

    @Column(nullable = false)
    private String preferences;
}
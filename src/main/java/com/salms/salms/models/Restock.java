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
@Table(name = "tbl_restock")
public class Restock implements Serializable {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String currentQuantity;

    @Column(nullable = false)
    private BigDecimal buyingPrice;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Instant restockDate;

    @Column(nullable = false)
    private String restockQuantity;
}

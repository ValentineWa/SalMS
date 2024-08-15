package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "tbl_services")
public class Service implements Serializable {

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private Instant duration;
    //How long the service takes

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

}
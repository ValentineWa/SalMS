package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
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

    @ManyToOne
    @Column(nullable = false)
    private Customers customers;

    @ManyToOne
    @Column(nullable = false)
    private Staff staff;

    @Column(nullable = false)
    private Instant appDate;

    @Column(nullable = false)
    private Instant time;

    @ManyToMany
    @Column(nullable = false)
    private Service service;

    @Column(nullable = false)
    private String clientPreferences;
}
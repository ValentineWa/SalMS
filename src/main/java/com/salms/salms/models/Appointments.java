package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
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
    @JoinColumn(name = "customers_id", referencedColumnName = "id", nullable=false)
    private Customers customers;

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable=false)
    private Staff staff;

    @Column(nullable = false)
    private Instant appDate;

    @Column(nullable = false)
    private Instant time;

    @ManyToMany
    @JoinTable(
            name = "appointment_service",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "solutions_id"))
    private List<Solutions> solutions;

    @Column(nullable = false)
    private String clientPreferences;
}
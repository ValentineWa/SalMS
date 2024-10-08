package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "tbl_customers")
public class Customers implements Serializable {

    @Id
    @Column(nullable = false, columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate startDate;
    // When they started the service

    @Column(nullable = false)
    private Instant creationDate;

    //A customer can have many appoinyments generally
    @OneToMany(mappedBy = "customers")
    private Set<Appointments> appointments = new HashSet<>();

    //A customer can have many services in one appointment
    @ManyToMany
    @JoinTable(
            name = "customer_services",
            joinColumns = @JoinColumn(name = "customers_id"),
            inverseJoinColumns = @JoinColumn(name = "solutions_id"))

    private Set<Solutions> services = new HashSet<>();

}

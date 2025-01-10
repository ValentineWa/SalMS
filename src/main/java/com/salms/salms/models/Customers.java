package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "tbl_customers")
public class Customers implements Serializable {

    @Id
    @Column(nullable = false, columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate startDate;
    // When they started the service

    @Column(nullable = false)
    private Instant creationDate;

    //A customer can have many appointments generally
    @OneToMany(mappedBy = "customers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointments> appointments = new ArrayList<>();

    //A customer can have many services in one appointment.
    @ManyToMany
    @JoinTable(
            name = "customer_services",
            joinColumns = @JoinColumn(name = "customers_id"),
            inverseJoinColumns = @JoinColumn(name = "solutions_id"))

    private List<Solutions> services = new ArrayList<>();

}

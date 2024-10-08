package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "tbl_appointments")
public class Appointments implements Serializable {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    //A customer can have many appointments
    @ManyToOne
    @JoinColumn(name = "customers_id", referencedColumnName = "id", nullable=false)
    private Customers customers;

    //A staff can have many appointments
    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable=false)
    private Staff staff;

    @Column(nullable = false)
    private Instant appDate;

    @Column(nullable = false)
    private Instant time;

    @Column(nullable = false)
    private AppStatus appStatus;

    @Column(nullable = false)
    private String clientPreferences;

    //A customer can have many services in an appointment
    @ManyToMany
    @JoinTable(
            name = "appointment_Services",
            joinColumns = @JoinColumn(name = "appointments_id"),
            inverseJoinColumns = @JoinColumn(name = "solutions_id")
    )
    private Set<Solutions> services = new HashSet<>();


    public enum AppStatus {
        COMPLETED,
        RESCHEDULED,
        CANCELED
    }

}
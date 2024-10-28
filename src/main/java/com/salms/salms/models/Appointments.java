package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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
    private LocalDate appDate;

    @Column(nullable = false)
    private String time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppStatus appStatus;

    @Column(nullable = false)
    private String clientPreferences;


    ////What's orphanremoval?
    @ToString.Exclude
    @OneToMany(mappedBy = "appointments", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentDetails> appointmentDetails = new ArrayList<>();

    @Column(nullable = false)
    private Instant createdOn;

    @Column(nullable = false)
    private Instant updatedOn;

    public enum AppStatus {
        OPEN, //Customer has created the appointment, not done but booked
        IN_PROGRESS,    //Customer is currently receiving the services
        CONFIRMED,       // All services confirmed and staff added and total amount provided. Final Step before payment
        COMPLETED,       // All services have been provided and appointed is finished, payment done.
    }

}
package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tbl_appointment_details")
public class AppointmentDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointments appointments;

        //A customer can have many services in an appointment
    @ManyToMany
    @JoinTable(
            name = "appointment_details_services",
            joinColumns = @JoinColumn(name = "appointment_details_id"),
            inverseJoinColumns = @JoinColumn(name = "solutions_id")
    )
    private Set<Solutions> services = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(nullable = false)
    private int duration;
    //How long the service takes

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Instant creationOn;
    @Column(nullable = false)
    private Instant updatedOn;


}

package com.salms.salms.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbl_appointment_details")
public class AppointmentDetails implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointments appointments;

        //A customer can have many services in an appointment
    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "appointment_details_services",
            joinColumns = @JoinColumn(name = "appointment_details_id"),
            inverseJoinColumns = @JoinColumn(name = "solutions_id")
    )


    private List<Solution> services = new ArrayList<>();

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

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
@Table(name = "tbl_staff")
public class Staff implements Serializable {

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String staffName;

    @Column(nullable = false)
    private String staffAlias;

    @Column(nullable = false)
    private String idNumber;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate startDate;
    //When the staff started worked here

    @Column(nullable = false)
    private int yearsOfExperience;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private String physicalAddress;

    @Column(nullable = false)
    private Instant creationDate;
    //When the staff started worked here

    //A staff can perform many services
    @ManyToMany
    @JoinTable(
            name = "staff_solutions",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "solutions_id"))

    private Set<Solutions> solutions = new HashSet<>();
}
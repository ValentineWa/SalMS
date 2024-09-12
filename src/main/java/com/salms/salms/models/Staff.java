package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
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
    private Instant joinDate;
    //When the staff started worked here

    @ManyToMany
    @JoinTable(
            name = "staff_service",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Solutions> services = new HashSet<>();
}


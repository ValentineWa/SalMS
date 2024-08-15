package com.salms.salms.models;

import jakarta.persistence.*;

import java.util.UUID;

public class Expertise {

    //Jpin services and Staff

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToMany
    @JoinColumn(name = "staffName", referencedColumnName = "staffName", nullable=false)
    private Staff staff;

    @ManyToMany
    @JoinColumn(name = "staffName", referencedColumnName = "staffName", nullable=false)
    private Service service;

//proceed from here



}

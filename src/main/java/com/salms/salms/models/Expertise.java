package com.salms.salms.models;

import jakarta.persistence.*;

import java.util.UUID;

public class Expertise {



    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;



}
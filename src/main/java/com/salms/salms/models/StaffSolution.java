package com.salms.salms.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "staff_solutions")
@Getter
@Setter
public class StaffSolution {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "solution_id", nullable = false)
    private Solution solution;

    // Future-proof fields
    private BigDecimal customPrice;
    private String skillLevel;
    private boolean active;
    //solution --> staffServices <--- services
}

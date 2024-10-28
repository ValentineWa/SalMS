package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "tbl_services")
public class Solutions implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private int duration;
    //How long the service takes

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    //Many staff can perform many services
    @ToString.Exclude
    @ManyToMany(mappedBy = "solutions")
    private List<Staff> staff = new ArrayList<>();

}
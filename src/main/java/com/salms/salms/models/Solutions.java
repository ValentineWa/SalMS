package com.salms.salms.models;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
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
    @ManyToMany(mappedBy = "solutions")
    private Set<Staff> staff = new HashSet<>();

//    //Many customers can book many services
//    @ManyToMany(mappedBy = "solutions")
//    private Set<Customers> customers = new HashSet<>();

}
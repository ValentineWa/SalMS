package com.salms.salms.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolutionRequest {

    @NotNull private String serviceName;
    @NotNull private int duration;
    //time in minutes
    @NotNull private BigDecimal price;
    @NotNull private String description;

//    @ManyToMany(mappedBy = "services")
//    private Set<Staff> staff = new HashSet<>();
}

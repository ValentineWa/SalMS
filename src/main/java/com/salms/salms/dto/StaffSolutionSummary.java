package com.salms.salms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffSolutionSummary {

    private UUID id;
    private String name;
    private boolean active;
    private BigDecimal customPrice;
    private String skillLevel;
}

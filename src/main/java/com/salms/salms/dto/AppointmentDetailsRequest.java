package com.salms.salms.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetailsRequest {

    private UUID id;
    private UUID appointmentId;
    private String serviceName;
    private BigDecimal price;
    private int duration;
    private UUID staffId;
    private String staffName;
    private String staffAlias;
    private String customerName;
    private LocalDate appDate;
    private Instant time;



}

package com.salms.salms.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomersRequest {
    @NotNull private String firstName;
     private String lastName;
    @NotNull private String phoneNumber;
    @NotNull private LocalDate startDate;
    @NotNull private Instant creationDate;
}
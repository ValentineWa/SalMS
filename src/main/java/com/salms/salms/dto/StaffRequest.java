package com.salms.salms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffRequest {

    @NotNull private String staffName;
    @NotNull private String staffAlias;
    @NotNull private String idNumber;
    @NotNull private String phoneNumber;
    @NotNull private LocalDate startDate;
    @NotNull private int yearsOfExperience;
    @NotNull private String nationality;
    @NotNull private String physicalAddress;
    @NotNull private Instant creationDate;

}
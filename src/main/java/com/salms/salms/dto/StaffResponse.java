package com.salms.salms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class StaffResponse extends GenericApiResponseContent{
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("staffName")
    private String staffName;

    @JsonProperty("staffAlias")
    private String staffAlias;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("idNumber")
    private String idNumber;

    @JsonProperty("startDate")
    private LocalDate startDate;

    @JsonProperty("yearOfExperience")
    private int yearOfExperience;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("physicalAddress")
    private String physicalAddress;

    @JsonProperty("services")
    private List services;

}

package com.salms.salms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class StaffResponse extends GenericApiResponseContent{
    @JsonProperty("staffName")
    private String staffName;

    @JsonProperty("staffAlias")
    private String staffAlias;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("idNumber")
    private String idNumber;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("yearOfExperience")
    private String yearOfExperience;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("physicalAddress")
    private String physicalAddress;

    @JsonProperty("services")
    private String services;

}

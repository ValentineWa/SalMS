package com.salms.salms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salms.salms.models.Solutions;
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
public class CustomersResponse extends GenericApiResponseContent {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("startDate")
    private LocalDate startDate;
}
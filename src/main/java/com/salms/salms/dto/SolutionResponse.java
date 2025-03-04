package com.salms.salms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class SolutionResponse extends GenericApiResponseContent {
    @JsonProperty("id")
    private String id;

    @JsonProperty("serviceName")
    private String serviceName;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("description")
    private String description;

}
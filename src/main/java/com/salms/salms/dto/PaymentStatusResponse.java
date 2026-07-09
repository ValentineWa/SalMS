package com.salms.salms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class PaymentStatusResponse extends GenericApiResponseContent {

    @JsonProperty("appointment_id")
    private String appointmentId;

    @JsonProperty("expected_total")
    private BigDecimal expectedTotal;

    @JsonProperty("total_paid")
    private BigDecimal totalPaid;

    @JsonProperty("appointment_status")
    private String appointmentStatus;

}

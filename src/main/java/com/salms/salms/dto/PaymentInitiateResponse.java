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
public class PaymentInitiateResponse extends GenericApiResponseContent {

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("appointment_id")
    private String appointmentId;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("status")
    private String status;

    @JsonProperty("external_reference")
    private String externalReference;
}

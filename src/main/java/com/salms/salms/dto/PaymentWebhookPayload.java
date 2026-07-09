package com.salms.salms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class PaymentWebhookPayload {

    @JsonProperty("payment_id")
    private String paymentId; // our internal id (preferred)

    @JsonProperty("external_reference")
    private String externalReference; // provider ref if using that to lookup

    @JsonProperty("receipt_number")
    private String receiptNumber;

    @JsonProperty("paid_at")
    private Instant paidAt;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("payer_phone_number")
    private String payerPhoneNumber;
}

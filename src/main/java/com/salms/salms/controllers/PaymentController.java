package com.salms.salms.controllers;

import com.salms.salms.dto.ApiResponse;
import com.salms.salms.dto.PaymentInitiateResponse;
import com.salms.salms.dto.PaymentWebhookPayload;
import com.salms.salms.dto.PaymentStatusResponse;
import com.salms.salms.models.AppointmentDetails;
import com.salms.salms.models.Appointments;
import com.salms.salms.models.PaymentProvider;
import com.salms.salms.models.Payments;
import com.salms.salms.repositories.AppointmentRepository;
import com.salms.salms.repositories.PaymentsRepository;
import com.salms.salms.services.AppointmentService;
import com.salms.salms.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PaymentsRepository paymentsRepository;

    @PostMapping("/initiate/{appointmentId}")
    public ResponseEntity<ApiResponse<PaymentInitiateResponse>> initiatePayment(
            @PathVariable UUID appointmentId,
            @RequestParam(value = "phone", required = false) String phoneOverride) {

        Appointments appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        // Calculate total amount from appointment details
        BigDecimal totalAmount = Optional.ofNullable(appointment.getAppointmentDetails())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(AppointmentDetails::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Payments pending = paymentService.createPendingPayment(appointment, totalAmount);
        // Set optional metadata for initiation context
        if (phoneOverride != null && !phoneOverride.isBlank()) {
            pending.setPayerPhoneNumber(phoneOverride);
        }
        if (pending.getCurrency() == null) pending.setCurrency("KES");
        if (pending.getProvider() == null) pending.setProvider(PaymentProvider.MPESA);
        pending.setUpdatedOn(Instant.now());
        pending = paymentsRepository.save(pending);

        PaymentInitiateResponse body = PaymentInitiateResponse.builder()
                .paymentId(pending.getId().toString())
                .appointmentId(appointmentId.toString())
                .amount(pending.getAmount())
                .currency(pending.getCurrency())
                .status(pending.getStatus().name())
                .externalReference(pending.getExternalReference())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "Payment initiated (pending)", body));
    }

    // Mock webhook to mark a payment as paid
    @PostMapping("/webhook/mock")
    public ResponseEntity<ApiResponse<PaymentInitiateResponse>> webhookMock(
            @RequestBody PaymentWebhookPayload payload) {

        if (payload.getPaymentId() == null || payload.getPaymentId().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "payment_id is required", null));
        }

        UUID paymentId = UUID.fromString(payload.getPaymentId());
        Payments paid = paymentService.markPaymentPaid(
                paymentId,
                payload.getReceiptNumber(),
                payload.getExternalReference(),
                payload.getPaidAt(),
                payload.getAmount()
        );

        // Try completing appointment if fully paid
        Appointments updatedApt = appointmentService.completeAppointment(paid.getAppointments().getId());

        PaymentInitiateResponse body = PaymentInitiateResponse.builder()
                .paymentId(paid.getId().toString())
                .appointmentId(paid.getAppointments().getId().toString())// Do i get the total amount or the single payment happens to be the full amt?
                .amount(paid.getAmount())
                .currency(paid.getCurrency())
                .status(paid.getStatus().name())
                .externalReference(paid.getExternalReference())
                .build();

        String msg = "Payment updated to PAID; appointment status: " + updatedApt.getAppStatus();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, msg, body));
    }

    @GetMapping("/{appointmentId}/status")
    public ResponseEntity<ApiResponse<PaymentStatusResponse>> getPaymentStatus(@PathVariable UUID appointmentId) {
        Appointments appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        BigDecimal expectedTotal = Optional.ofNullable(appointment.getAppointmentDetails())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(AppointmentDetails::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPaid = paymentService.getTotalPaidForAppointment(appointmentId);

        PaymentStatusResponse status = PaymentStatusResponse.builder()
                .appointmentId(appointmentId.toString())
                .expectedTotal(expectedTotal)
                .totalPaid(totalPaid)
                .appointmentStatus(appointment.getAppStatus().name())
                .build();
        return ResponseEntity.ok(new ApiResponse<>(200, "Payment status", status));
    }
}
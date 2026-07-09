package com.salms.salms.services;


import com.salms.salms.models.Appointments;
import com.salms.salms.models.Payments;
import com.salms.salms.repositories.PaymentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    public Payments createPendingPayment(Appointments appointment, BigDecimal amount) {
        // Avoid creating duplicate pending payments for the same appointment
        Optional<Payments> existing = paymentsRepository
                .findTopByAppointmentsIdAndStatusOrderByCreatedOnDesc(
                        appointment.getId(), Payments.PaymentStatus.PENDING);

        if (existing.isPresent()) {
            Payments payment = existing.get();
            // If amount changed due to edits before payment initiation, update it
            if (amount != null && payment.getAmount() != null && amount.compareTo(payment.getAmount()) != 0) {
                payment.setAmount(amount);
                payment.setUpdatedOn(Instant.now());
                return paymentsRepository.save(payment);
            }
            return payment;
        }

        Payments payment = new Payments();
        payment.setAppointments(appointment);
        payment.setCustomers(appointment.getCustomers());
        payment.setAmount(amount);
        payment.setStatus(Payments.PaymentStatus.PENDING);
        payment.setCreatedOn(Instant.now());
        payment.setUpdatedOn(Instant.now());
        return paymentsRepository.save(payment);
    }

    public BigDecimal getTotalPaidForAppointment(UUID appointmentId) {
        return paymentsRepository.sumAmountByAppointmentIdAndStatus(
                appointmentId,
                Payments.PaymentStatus.PAID
        );
    }

    public Payments markPaymentPaid(UUID paymentId,
                                    String receiptNumber,
                                    String externalReference,
                                    Instant paidAt,
                                    BigDecimal amountOverride) {
        Payments payment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        if (amountOverride != null) {
            payment.setAmount(amountOverride);
        }
        payment.setReceiptNumber(receiptNumber);
        payment.setExternalReference(externalReference);
        payment.setPaymentDate(paidAt != null ? paidAt : Instant.now());
        payment.setStatus(Payments.PaymentStatus.PAID);
        payment.setUpdatedOn(Instant.now());
        return paymentsRepository.save(payment);
    }
}

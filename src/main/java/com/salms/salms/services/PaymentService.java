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
}

package com.salms.salms.repositories;

import com.salms.salms.models.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Optional;

public interface PaymentsRepository extends JpaRepository<Payments, UUID> {
    Optional<Payments> findTopByAppointmentsIdAndStatusOrderByCreatedOnDesc(UUID appointmentId, Payments.PaymentStatus status);
}

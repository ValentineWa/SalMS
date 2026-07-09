package com.salms.salms.repositories;

import com.salms.salms.models.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import java.util.Optional;
import java.math.BigDecimal;

public interface PaymentsRepository extends JpaRepository<Payments, UUID> {
    Optional<Payments> findTopByAppointmentsIdAndStatusOrderByCreatedOnDesc(UUID appointmentId, Payments.PaymentStatus status);

    @Query("select coalesce(sum(p.amount), 0) from Payments p where p.appointments.id = :appointmentId and p.status = :status")
    BigDecimal sumAmountByAppointmentIdAndStatus(@Param("appointmentId") UUID appointmentId,
                                                 @Param("status") Payments.PaymentStatus status);
}

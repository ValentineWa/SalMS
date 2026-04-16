package com.salms.salms.repositories;

import com.salms.salms.models.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointments, UUID> {

    Appointments findByCustomersPhoneNumberAndAppDate(String phoneNumber, LocalDate appdate);
}
package com.salms.salms.repositories;

import com.salms.salms.models.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.time.LocalDate;

public interface AppointmentRepository extends JpaRepository<Appointments, Long> {

    Appointments findAppointmentsByPhoneNumberAndDate(String phoneNumber, LocalDate date);
}

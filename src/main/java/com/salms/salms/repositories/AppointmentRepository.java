package com.salms.salms.repositories;

import com.salms.salms.models.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointments, Long> {
}

package com.salms.salms.repositories;

import com.salms.salms.models.AppointmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentDetailsRepository extends JpaRepository<AppointmentDetails, UUID> {


}

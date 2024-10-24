package com.salms.salms.dto;

import com.salms.salms.models.Appointments;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {
    @NotNull private String fullName;
    @NotNull private String phoneNumber;
    @NotNull private String staffAlias;  //Can be a walk in so you assign a staff or the customer says who they want
    @NotNull LocalDate appDate;
    @NotNull String time;
    @NotNull Appointments.AppStatus appStatus;
    @NotNull String clientPreferences;
    @NotNull List<String> servicesName;

}
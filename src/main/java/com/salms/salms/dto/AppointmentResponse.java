package com.salms.salms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salms.salms.models.Appointments;
import com.salms.salms.models.Solutions;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class AppointmentResponse extends GenericApiResponseContent {

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("staff_alias")
    private String staffAlias;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("app_date")
    private LocalDate appDate;

    @JsonProperty("time")
    private String time;

    @JsonProperty("client_preferences")
    private String clientPreferences;

    @JsonProperty("services_name")
    private List<Solutions> servicesName;


}
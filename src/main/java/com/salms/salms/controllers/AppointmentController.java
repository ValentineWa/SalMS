package com.salms.salms.controllers;

import com.salms.salms.dto.*;
import com.salms.salms.models.Appointments;
import com.salms.salms.models.Customers;
import com.salms.salms.repositories.AppointmentRepository;
import com.salms.salms.services.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/booking")
@Slf4j
public class AppointmentController {


        @Autowired
        private AppointmentRepository appointmentRepository;
        @Autowired
        private AppointmentService appointmentService;


        @PostMapping("/createNew")
        public ResponseEntity<ApiResponse<AppointmentResponse>> bookAppointment(@RequestBody AppointmentRequest appointmentRequest) {

                Appointments bookApp = appointmentService.bookAppointment(appointmentRequest);
                AppointmentResponse response = appointmentService.createAppointmentResponse(bookApp);
                return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200,"Your Appointment has been created successfully", response));

        }

        @GetMapping("/getAllBookings")
        public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAllBookings() {
            List<Appointments> allAppointments = appointmentRepository.findAll();
            List<AppointmentResponse> aptResponses = allAppointments.stream()
                    .map(appointmentService::createAppointmentResponse)
                    .toList();

        ApiResponse<List<AppointmentResponse>> response = new ApiResponse<>(200, "Appointments retrieved successfully", aptResponses);
        return ResponseEntity.ok(response);
        }

    @DeleteMapping("/deleteBookings/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> deleteBooking(@PathVariable UUID id){

            appointmentService.deleteAppointmentById(id);
            ApiResponse<AppointmentResponse> response = new ApiResponse<>(200, "Customer Deleted Successfully",null);
            return ResponseEntity.ok(response);
    }

    @PutMapping("/updateBookings/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateBookings(
            @PathVariable UUID id,
            @RequestBody Appointments updatedBookings) {

        Appointments apt = appointmentService.updateBookings(id, updatedBookings);
        AppointmentResponse response = appointmentService.createAppointmentResponse(apt);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        200,
                        "Your Appointment Has Been Updated Successfully",
                        response
                ));
    }

}
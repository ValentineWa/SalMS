package com.salms.salms.controllers;

import com.salms.salms.dto.*;
import com.salms.salms.models.Appointments;
import com.salms.salms.repositories.AppointmentRepository;
import com.salms.salms.services.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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
            try{
                Appointments bookApp = appointmentService.bookAppointment(appointmentRequest);
                AppointmentResponse response = appointmentService.createAppointmentResponse(bookApp);
                return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200,"Your Appointment has been created successfully", response));

            } catch(Exception e)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
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


}

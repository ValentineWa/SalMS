package com.salms.salms.controllers;

import com.salms.salms.dto.*;
import com.salms.salms.models.Appointments;
import com.salms.salms.models.Staff;
import com.salms.salms.repositories.AppointmentRepository;
import com.salms.salms.services.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


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
                AppointmentResponse response = appointmentService.createAppointment(bookApp);
                return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200,"Your Appointmente has been created successfully", response));

            } catch(Exception e)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }

        @GetMapping("/getAllBookings")
        public ResponseEntity<List> getAllBookings() {
            List<Appointments> allAppointments = appointmentRepository.findAll();
            return ResponseEntity.status(HttpStatus.FOUND).body(allAppointments);
        }

}
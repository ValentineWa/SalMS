package com.salms.salms.controllers;

import com.salms.salms.dto.AppointmentRequest;
import com.salms.salms.dto.AppointmentResponse;
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
        public ResponseEntity<AppointmentResponse> bookAppointment(@RequestBody AppointmentRequest appointmentRequest) {
            try{
                Appointments bookApp = appointmentService.bookAppointment(appointmentRequest);
                AppointmentResponse responseDto = appointmentService.createAppointment(bookApp);
                return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
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
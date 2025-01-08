package com.salms.salms.controllers;

import com.salms.salms.dto.StaffRequest;
import com.salms.salms.exceptions.GlobalExceptionHandler;
import com.salms.salms.models.Staff;
import com.salms.salms.repositories.StaffRepository;
import com.salms.salms.services.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/staff")
@Slf4j
public class StaffController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @PostMapping("/createNew")
    public ResponseEntity<?> createStaff(@RequestBody StaffRequest staffRequest) {
        String idNumber = staffRequest.getIdNumber();

        Optional<Staff> existingStaff = staffRepository.findByIdNumber(idNumber);
//
//        if (existingStaff.isPresent()) {
//            return globalExceptionHandler.handlePhoneNumberExists(idNumber);
//        }
        Staff newStaff = staffService.createStaff(staffRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStaff);
    }

    @GetMapping("/getAllStaff")
    public ResponseEntity<List> getAllStaff() {
        List<Staff> allStaff = staffRepository.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(allStaff);
    }



}
package com.salms.salms.controllers;

import com.salms.salms.dto.SolutionRequest;
import com.salms.salms.exceptions.GlobalExceptionHandler;
import com.salms.salms.models.Solutions;
import com.salms.salms.repositories.SolutionRepository;
import com.salms.salms.services.SolutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/services")
@Slf4j
public class SolutionController {

    @Autowired
    private SolutionService solutionService;
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;


    @PostMapping("/createNew")
    public ResponseEntity<?> createCustomer(@RequestBody SolutionRequest solutionRequest) {
        String serviceName = solutionRequest.getServiceName();
            Optional<Solutions> existingService = solutionRepository.findByServiceName(serviceName);

        if (existingService.isPresent()) {
            return globalExceptionHandler.handlePhoneNumberExists(serviceName);
        }
        Solutions newService = solutionService.createService(solutionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newService);
    }

    @GetMapping("/getAllServices")
    public ResponseEntity<List> getAllServices() {
        List<Solutions> allServices = solutionRepository.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(allServices);
    }

}

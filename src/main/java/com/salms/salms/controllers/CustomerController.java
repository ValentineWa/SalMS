package com.salms.salms.controllers;


import com.salms.salms.dto.CustomersRequest;
import com.salms.salms.exceptions.CustomerAlreadyExistsException;
import com.salms.salms.exceptions.CustomerNotFoundException;
import com.salms.salms.exceptions.GlobalExceptionHandler;
import com.salms.salms.models.Customers;
import com.salms.salms.repositories.CustomerRepository;
import com.salms.salms.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;


    @PostMapping("/createNew")
    public ResponseEntity<?> createCustomer(@RequestBody CustomersRequest customerRequest) {
        String phoneNumber = customerRequest.getPhoneNumber();
        Customers existingCustomer = customerRepository.findByPhoneNumber(phoneNumber);

        if (existingCustomer != null) {
            return globalExceptionHandler.handlePhoneNumberExists(phoneNumber);
        }
        Customers newCustomer = customerService.createCustomer(customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    @GetMapping("/getAllCustomers")
        public ResponseEntity<List> getAllCustomers() {
        List<Customers> allCustomers = customerRepository.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(allCustomers);
    }

}
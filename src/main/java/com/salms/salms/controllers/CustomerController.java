package com.salms.salms.controllers;


import com.salms.salms.dto.*;
import com.salms.salms.exceptions.CustomerAlreadyExistsException;
import com.salms.salms.exceptions.CustomerNotFoundException;
import com.salms.salms.exceptions.GlobalExceptionHandler;
import com.salms.salms.models.Appointments;
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
//    public ResponseEntity<?> createCustomer(@RequestBody CustomersRequest customerRequest) {
//        String phoneNumber = customerRequest.getPhoneNumber();
//        Customers existingCustomer = customerRepository.findByPhoneNumber(phoneNumber);
//
//        if (existingCustomer != null) {
//            return globalExceptionHandler.handlePhoneNumberExists(phoneNumber);
//        }
//        Customers newCustomer = customerService.createCustomer(customerRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
//    }
    public ResponseEntity<CustomersResponse> createCustomer(@RequestBody CustomersRequest customerRequest) {
        String phoneNumber = customerRequest.getPhoneNumber();
        Customers existingCustomer = customerRepository.findByPhoneNumber(phoneNumber);


        Customers newCustomer = customerService.createCustomer(customerRequest);

        // Create an instance of CustomersResponse
        CustomersResponse response = CustomersResponse.builder()
                .firstName(newCustomer.getFirstName()) // Assuming getFirstName() exists in Customers
                .lastName(newCustomer.getLastName())   // Assuming getLastName() exists in Customers
                .phoneNumber(newCustomer.getPhoneNumber()) // Assuming getPhoneNumber() exists in Customers
                .startDate(newCustomer.getStartDate()) // Assuming getStartDate() exists in Customers
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/getAllCustomers")
//        public ResponseEntity<List> getAllCustomers() {
//        List<Customers> allCustomers = customerRepository.findAll();
//        return ResponseEntity.status(HttpStatus.FOUND).body(allCustomers);
//    }

    public ResponseEntity<ApiResponse<List<CustomersResponse>>> getAllCustomers() {
        List<Customers> allCustomers = customerRepository.findAll();

        // Convert Customers to CustomersResponse if needed
        List<CustomersResponse> customerResponses = allCustomers.stream()
                .map(customer -> new CustomersResponse(
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getPhoneNumber(),
                        customer.getStartDate()))
                .toList();

        ApiResponse<List<CustomersResponse>> response = new ApiResponse<>(200, "Customers retrieved successfully", customerResponses);
        return ResponseEntity.ok(response);
    }

}
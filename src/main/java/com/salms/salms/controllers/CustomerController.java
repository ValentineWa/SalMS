package com.salms.salms.controllers;


import com.salms.salms.dto.*;
import com.salms.salms.exceptions.CustomerAlreadyExistsException;
import com.salms.salms.exceptions.CustomerNotFoundException;
import com.salms.salms.exceptions.GlobalExceptionHandler;
import com.salms.salms.models.Appointments;
import com.salms.salms.models.Customers;
import com.salms.salms.repositories.CustomerRepository;
import com.salms.salms.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
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

    public ResponseEntity<CustomersResponse> createCustomer(@RequestBody CustomersRequest customerRequest) {
        String phoneNumber = customerRequest.getPhoneNumber();
        Customers existingCustomer = customerRepository.findByPhoneNumber(phoneNumber);


        Customers newCustomer = customerService.createCustomer(customerRequest);

        CustomersResponse response = CustomersResponse.builder()
                .firstName(newCustomer.getFirstName())
                .lastName(newCustomer.getLastName())
                .phoneNumber(newCustomer.getPhoneNumber())
                .startDate(newCustomer.getStartDate())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<ApiResponse<List<CustomersResponse>>> getAllCustomers() {
        List<Customers> allCustomers = customerRepository.findAll();

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
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
       try {
           customerService.deleteCustomer(id);
           return ResponseEntity.ok("Customer Deleted Successfully");
       } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       } catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred While Deleting The Customer");
       }
       }



}
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
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


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

    public ResponseEntity<ApiResponse<CustomersResponse>> createCustomer(@RequestBody CustomersRequest customerRequest) {
        String phoneNumber = customerRequest.getPhoneNumber();
        Customers existingCustomer = customerRepository.findByPhoneNumber(phoneNumber);


        Customers newCustomer = customerService.createCustomer(customerRequest);

        CustomersResponse response = CustomersResponse.builder()
                .firstName(newCustomer.getFirstName())
                .lastName(newCustomer.getLastName())
                .phoneNumber(newCustomer.getPhoneNumber())
                .startDate(newCustomer.getStartDate())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200,"Customers created successfully", response));
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<ApiResponse<List<CustomersResponse>>> getAllCustomers() {
        List<Customers> allCustomers = customerRepository.findAll();

        List<CustomersResponse> customerResponses = allCustomers.stream()
                .map(customer -> new CustomersResponse(
                        customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getPhoneNumber(),
                        customer.getStartDate()))
                .toList();

        ApiResponse<List<CustomersResponse>> response = new ApiResponse<>(200, "Customers retrieved successfully", customerResponses);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<ApiResponse<CustomersResponse>> deleteCustomer(@PathVariable UUID id){
       try {
           customerService.deleteCustomerById(id);
           ApiResponse<CustomersResponse> response = new ApiResponse<>(200, "Customer Deleted Successfully",null);
           return ResponseEntity.ok(response);
       } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Customer Not Found", null));
       } catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "An Error Occurred While Deleting The Customer", null ));
       }
       }


    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<Customers> updateCustomer(@PathVariable UUID id, @RequestBody Customers updatedCustomer){

            Customers customer = customerService.updateCustomer(id, updatedCustomer);
            return ResponseEntity.ok(customer);

    }

}
package com.salms.salms.controllers;


import com.salms.salms.models.Customers;
import com.salms.salms.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

//    @PostMapping
//    public ResponseEntity<Customers> createCustomer(@RequestBody Customers customers) {
//        return ResponseEntity.ok(customerService.saveCustomer(customers));
//    }

//    @GetMapping
//    public ResponseEntity<List<Customers>> getAllCustomers() {
//        return ResponseEntity.ok(customerService.getAllCustomers());
//    }

}

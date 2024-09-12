package com.salms.salms.services;

import com.salms.salms.models.Customers;
import com.salms.salms.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customers createLoan(String fullName, String phoneNumber, Instant startDate){
        Customers customer = new Customers();
        customer.setId(UUID.randomUUID());
        customer.setFullName(fullName);
        customer.setPhoneNumber(phoneNumber);
        customer.setStartDate(startDate);
        customer.setCreationDate(Instant.now());
        customerRepository.save(customer);
        return customer;
    }




}

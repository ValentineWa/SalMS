package com.salms.salms.services;

import com.salms.salms.dto.CustomersRequest;
import com.salms.salms.models.Customers;
import com.salms.salms.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customers createCustomer(CustomersRequest customerRequest){
        Customers customer = new Customers();
        customer.setId(UUID.randomUUID());
        customer.setFullName(customerRequest.getFullName());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setStartDate(customerRequest.getStartDate());
        customer.setCreationDate(Instant.now());
        customerRepository.save(customer);
        log.info("CUSTOMER [%s] HAS BEEN CREATED SUCCESSFULLY", customerRequest.getFullName());
        return customer;
    }


}

package com.salms.salms.services;

import com.salms.salms.dto.CustomersRequest;
import com.salms.salms.exceptions.CustomerAlreadyExistsException;
import com.salms.salms.exceptions.CustomerNotFoundException;
import com.salms.salms.models.Customers;
import com.salms.salms.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
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

        String phoneNumber = customerRequest.getPhoneNumber();
        Customers existingCustomer = customerRepository.findByPhoneNumber(phoneNumber);

        if (existingCustomer != null) {
            throw new CustomerAlreadyExistsException("Customer with phone number " + phoneNumber + " already exists.");
        }

        Customers customer = new Customers();
        customer.setId(UUID.randomUUID());
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setStartDate(customerRequest.getStartDate());
        customer.setCreationDate(Instant.now());
        customerRepository.save(customer);
        log.info("CUSTOMER [%s] HAS BEEN CREATED SUCCESSFULLY", customerRequest.getFirstName());
        return customer;
    }

    public void deleteCustomerById (UUID id){

        if (customerRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Customer With ID " + id + "Not Found" );
        }
        customerRepository.deleteById(id);
    }

    public Customers updateCustomer (UUID id, Customers updatedCustomer){

        Customers existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + id + " not found"));
        existingCustomer.setFirstName( updatedCustomer.getFirstName());
        existingCustomer.setLastName(updatedCustomer.getLastName());
        existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
        existingCustomer.setStartDate(updatedCustomer.getStartDate());
        return customerRepository.save(existingCustomer);
    }

}
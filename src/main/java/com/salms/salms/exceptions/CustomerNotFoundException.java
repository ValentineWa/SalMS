package com.salms.salms.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String phoneNumber) {
        super(String.format("Customer with phone number [%s] could not be found.", phoneNumber));
    }
}
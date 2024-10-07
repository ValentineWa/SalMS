package com.salms.salms.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String customerId) {
        super(String.format("Customer [%s] could not be found.", customerId));
    }
}
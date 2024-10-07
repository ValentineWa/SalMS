package com.salms.salms.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String customerId) {
        super(String.format("Customer [%s] Already Exists.", customerId));
    }
}

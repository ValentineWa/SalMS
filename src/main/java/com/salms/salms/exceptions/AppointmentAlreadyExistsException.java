package com.salms.salms.exceptions;

public class AppointmentAlreadyExistsException extends RuntimeException {
    public AppointmentAlreadyExistsException(String customerId) {
        super(String.format("Customer [%s] Already Exists.", customerId));
    }
}

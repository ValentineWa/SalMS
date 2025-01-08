package com.salms.salms.exceptions;

import org.springframework.http.HttpStatus;

public class CustomeException extends RuntimeException{
    HttpStatus status;
    String message;

    CustomeException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage(){
        return message;
    }
}

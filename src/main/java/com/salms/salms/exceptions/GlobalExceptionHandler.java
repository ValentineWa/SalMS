package com.salms.salms.exceptions;

import com.salms.salms.dto.ErrorCode;
import com.salms.salms.dto.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    private static final Map<Class<? extends Throwable>, ErrorCode> EXCEPTION_MAPPINGS =
            new HashMap<>();

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    static{

        EXCEPTION_MAPPINGS.put(CustomerAlreadyExistsException.class, ErrorCode.CUSTOMER_ALREADY_EXISTS);
        EXCEPTION_MAPPINGS.put(CustomerNotFoundException.class, ErrorCode.CUSTOMER_NOT_FOUND);
        EXCEPTION_MAPPINGS.put(AppointmentAlreadyExistsException.class, ErrorCode.APPOINTMENT_ALREADY_EXISTS);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }










@ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handlePhoneNumberExists(CustomerAlreadyExistsException ex) {
        log.error("Customer you are trying to create already exists.", ex.getMessage(), ex);
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ErrorCode.CUSTOMER_ALREADY_EXISTS.getErrCode(), ErrorCode.CUSTOMER_ALREADY_EXISTS.getHttpStatus(),ErrorCode.CUSTOMER_ALREADY_EXISTS.getErrMsgKey()
                );
        return ResponseEntity.status(ErrorCode.CUSTOMER_ALREADY_EXISTS.getHttpStatus()).body(errorResponse);
    }


    public ResponseEntity<ErrorResponseDto> appointmentAlreadyExists(String phoneNumber, LocalDate appDate) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ErrorCode.APPOINTMENT_ALREADY_EXISTS.getErrCode(), ErrorCode.APPOINTMENT_ALREADY_EXISTS.getHttpStatus(),ErrorCode.APPOINTMENT_ALREADY_EXISTS.getErrMsgKey()
        );
        return ResponseEntity.status(ErrorCode.APPOINTMENT_ALREADY_EXISTS.getHttpStatus()).body(errorResponse);
    }

}

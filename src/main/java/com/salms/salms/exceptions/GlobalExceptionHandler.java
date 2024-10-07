package com.salms.salms.exceptions;

import com.salms.salms.dto.ErrorCode;
import com.salms.salms.dto.ErrorResponseDto;
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

        EXCEPTION_MAPPINGS.put(CustomerNotFoundException.class, ErrorCode.CUSTOMER_NOT_FOUND);

    }

    public ResponseEntity<ErrorResponseDto> handlePhoneNumberExists(String phoneNumber) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ErrorCode.CUSTOMER_ALREADY_EXISTS.getHttpStatus(),ErrorCode.CUSTOMER_ALREADY_EXISTS.getErrMsgKey(), ErrorCode.CUSTOMER_ALREADY_EXISTS.getErrCode()
                );
        return ResponseEntity.status(ErrorCode.CUSTOMER_ALREADY_EXISTS.getHttpStatus()).body(errorResponse);
    }
}

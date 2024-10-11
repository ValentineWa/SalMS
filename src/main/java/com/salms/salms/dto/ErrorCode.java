package com.salms.salms.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public enum ErrorCode {

    GENERIC_ERROR(
            "SALMA-0001",
            "The system is unable to complete the request. Contact system support.",
            HttpStatus.INTERNAL_SERVER_ERROR),
    CUSTOMER_NOT_FOUND("SALMA-0002", "Requested customer not found.", HttpStatus.NOT_FOUND),
    CUSTOMER_ALREADY_EXISTS("SALMA-0003", "Requested customer %s already exists in the system.", HttpStatus.CONFLICT),
    APPOINTMENT_ALREADY_EXISTS("SALMA-0004", "You already have an appointment scheduled for this day.",HttpStatus.CONFLICT);

    private final String errCode;
    private final String errMsgKey;
    private final HttpStatus httpStatus;

    private ErrorCode(final String errCode, final String errMsgKey, final HttpStatus httpStatus) {
        this.errCode = errCode;
        this.errMsgKey = errMsgKey;
        this.httpStatus = httpStatus;
    }

    @JsonValue
    public String getErrCode() {
        return errCode;
    }

}

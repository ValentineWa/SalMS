package com.salms.salms.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApiResponse <T> {
    private int statusCode;
    private String message;
    private boolean success;
    private T data;

    public ApiResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.success = statusCode == 200; // Assuming 200 is a success
        this.data = data;
    }

}

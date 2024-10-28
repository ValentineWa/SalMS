package com.salms.salms.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Getter
@NoArgsConstructor
@ToString
public class ApiResponse <T extends GenericApiResponseContent> {

    private boolean success;
    private T data;
    private Error error;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Data
    public static class Error {
        private String message;
        private Map<String, Object> errors;

        public Error(String message, Map<String, Object> errors) {
            this.message = message;
            this.errors = errors;
        }
    }
}

package com.sirest.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private Map<String, String> validationErrors;

    public ApiError() {
    }

    public ApiError(LocalDateTime timestamp, int status, String error, Map<String, String> validationErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.validationErrors = validationErrors;
    }

}
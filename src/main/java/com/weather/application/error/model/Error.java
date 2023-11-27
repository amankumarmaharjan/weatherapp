package com.weather.application.error.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class Error {
    private HttpStatus status;

    private String code;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private String errorDetails;

    private Error() {
        timestamp = LocalDateTime.now();
    }

    public Error(HttpStatus status, String code, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.code = code;
        this.errorDetails = ex.getLocalizedMessage();
    }
}
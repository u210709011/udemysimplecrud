package com.etiya.udemy.core.crosscuttingconcerns.exceptions.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationProblemDetails {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private Map<String, String> validationErrors;
}

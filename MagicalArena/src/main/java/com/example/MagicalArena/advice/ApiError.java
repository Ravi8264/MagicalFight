package com.example.MagicalArena.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * ApiError class to represent details of an error in a structured way
 */
@AllArgsConstructor
@Data
@Builder
public class ApiError {

    // Error message providing the main reason for the error
    private String message;

    // HTTP status associated with the error
    private HttpStatus status;

    // List of sub-errors or detailed messages for complex errors
    private List<String> subErrors;
}

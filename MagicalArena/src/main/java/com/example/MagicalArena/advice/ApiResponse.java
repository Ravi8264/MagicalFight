package com.example.MagicalArena.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime timeStamp = LocalDateTime.now();
    private T data;
    private ApiError error;
    private boolean success;
    private String message;    // Added message field

    // Constructor with data
    public ApiResponse(T data) {
        this();
        this.data = data;
        this.success = true; // By default, success is true
    }

    // Constructor with error
    public ApiResponse(ApiError error) {
        this();
        this.error = error;
        this.success = false; // If there is an error, success is false
    }

    // Constructor with success flag, message, and data
    public ApiResponse(boolean success, String message, T data) {
        this();
        this.success = success;
        this.message = message;
        this.data = data;
    }
}

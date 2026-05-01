package com.bedmaster.module.common.exceptions;

import java.time.LocalDateTime;

// Structured JSON body returned to the client whenever an exception is caught by GlobalExceptionHandler.
// Every error response contains five fields so the caller always knows when, what, and where the failure occurred.
public class ErrorResponse {

    private LocalDateTime timestamp; // When the error occurred (server time)
    private int status;              // HTTP status code (e.g. 404, 500)
    private String error;            // Short HTTP reason phrase (e.g. "Not Found")
    private String message;          // Detailed description taken from the exception message
    private String path;             // The request URI that triggered the error

    // All-args constructor — GlobalExceptionHandler is the only caller; fields are immutable after creation.
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Getters only — no setters needed because this object is write-once and read by Jackson for serialization.
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
}

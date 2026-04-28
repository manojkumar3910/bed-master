package com.bedmaster.inventory.exception;

public class ErrorResponse {

    private final String error;
    private final Object message;

    public ErrorResponse(String error, Object message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public Object getMessage() {
        return message;
    }
}
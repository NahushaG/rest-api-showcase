package com.example.validation.exception;

public enum ErrorType {
    RESOURCE_NOT_FOUND("Resource Not Found"),
    INVALID_INPUT("Invalid Input"),
    INTERNAL_SERVER_ERROR("Internal Server Error");

    private final String message;

     ErrorType(String message) {
        this.message = message;
    }

}

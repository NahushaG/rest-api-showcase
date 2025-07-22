package com.example.validation.exception;

public class NoEmployeeFoundException extends RuntimeException {
    public NoEmployeeFoundException(String message) {
        super(message);
    }
}

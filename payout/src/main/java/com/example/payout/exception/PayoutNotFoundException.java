package com.example.payout.exception;

public class PayoutNotFoundException extends RuntimeException {
    public PayoutNotFoundException(String message) {
        super(message);
    }
}

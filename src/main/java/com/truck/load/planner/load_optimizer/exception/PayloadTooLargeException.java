package com.truck.load.planner.load_optimizer.exception;

public class PayloadTooLargeException extends RuntimeException {

    public PayloadTooLargeException(
            String message) {

        super(message);
    }
}

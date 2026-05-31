package com.truck.load.planner.load_optimizer.exception;
import com.truck.load.planner.load_optimizer.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        return ResponseEntity
                .badRequest()
                .body( new ErrorResponse(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation Failed",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(
            Exception ex) {

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(ex.getMessage());
    }

    @ExceptionHandler(
            PayloadTooLargeException.class)
    public ResponseEntity<ErrorResponse> handlePayloadTooLarge(PayloadTooLargeException ex) {
        return ResponseEntity
                .status(
                        HttpStatus.PAYLOAD_TOO_LARGE
                )
                .body(
                        new ErrorResponse(
                                Instant.now(),
                                413,
                                "Payload Too Large",
                                ex.getMessage()
                        )
                );
    }
}

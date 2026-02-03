package com.kaldis.chris.librij.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFound resourceNotFound) {
        Map<String, Object> response = new HashMap<>();

        response.put("message", resourceNotFound.getMessage());
        response.put("status", HttpStatus.NOT_FOUND);
        response.put("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}

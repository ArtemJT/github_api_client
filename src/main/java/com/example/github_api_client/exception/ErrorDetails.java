package com.example.github_api_client.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Artem Kovalov on 14.02.2024
 */
@Builder
public record ErrorDetails(HttpStatus status, String message) {

    public static ResponseEntity<ErrorDetails> getResponseEntity(String message, HttpStatus status) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(status)
                .message(message)
                .build();
        return new ResponseEntity<>(errorDetails, status);
    }
}

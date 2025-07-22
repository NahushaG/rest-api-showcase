package com.example.validation.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private List<ErrorMessage> message;
    private ErrorType error;
    private String path;
}

@Data
@Builder
@ToString
class ErrorMessage {
    private String field;
    private String message;
}
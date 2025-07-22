package com.example.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    //Handle Business Exception
    @ExceptionHandler(NoEmployeeFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoEmployeeFoundException(WebRequest request, NoEmployeeFoundException e) {
        ErrorMessage errorMessage = ErrorMessage.builder().message(e.getMessage()).build();
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(List.of(errorMessage))
                .error(ErrorType.RESOURCE_NOT_FOUND)
                .status(HttpStatus.NOT_FOUND)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(WebRequest request, MethodArgumentNotValidException e) {
        List<ErrorMessage> errorMessages = e.getBindingResult().getFieldErrors().stream()
                .map(error -> ErrorMessage.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage()).build()).toList();
        ErrorResponseDTO errorResponseDT = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(errorMessages)
                .error(ErrorType.INVALID_INPUT)
                .status(HttpStatus.BAD_REQUEST)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return new ResponseEntity<>(errorResponseDT, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericExceptions(WebRequest request, Exception e) {
        ErrorMessage errorMessage = ErrorMessage.builder().message(e.getMessage()).build();
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(List.of(errorMessage))
                .error(ErrorType.INTERNAL_SERVER_ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

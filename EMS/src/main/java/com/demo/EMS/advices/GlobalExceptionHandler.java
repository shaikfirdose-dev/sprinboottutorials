package com.demo.EMS.advices;

import com.demo.EMS.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorInfo errorInfo = ErrorInfo.builder()
                .httpStatus("NOT_FOUND")
                .message(ex.getMessage())
                .httpCode("404")
                .build();
        return buildAPIResponseConverter(errorInfo);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException ex) {
        ErrorInfo errorInfo = ErrorInfo.builder()
                .httpStatus("INTERNAL_SERVER_ERROR")
                .message(ex.getMessage())
                .httpCode("500")
                .build();
        return buildAPIResponseConverter(errorInfo);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorInfo errorInfo = ErrorInfo.builder()
                .httpStatus("BAD_REQUEST")
                .message("Validation failed")
                .httpCode("400")
                .errors(ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .toList())
                .build();
        return buildAPIResponseConverter(errorInfo);
    }


    private ResponseEntity<ApiResponse<?>> buildAPIResponseConverter(ErrorInfo apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), HttpStatus.valueOf(Integer.parseInt(apiError.getHttpCode())));
    }




}

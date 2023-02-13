package com.example.venly_test_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleExceptions(ConstraintViolationException exception) {
        return new ResponseEntity<>("Violations: " + exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.joining("; ")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistingPairException.class)
    public ResponseEntity<Object> handleExceptions(ExistingPairException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PathNotFoundException.class)
    public ResponseEntity<Object> handleExceptions(PathNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}

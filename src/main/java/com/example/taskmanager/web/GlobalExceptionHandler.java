package com.example.taskmanager.web;

import com.example.taskmanager.model.exception.ExceptionResponse;
import com.example.taskmanager.model.exception.TaskNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(TaskNotFoundException e) {
        return ResponseEntity
                .badRequest()
                .body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        return ResponseEntity
                .internalServerError()
                .body(new ExceptionResponse("Sorry, something went wrong..."));
    }
}

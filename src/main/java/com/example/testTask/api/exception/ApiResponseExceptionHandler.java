package com.example.testTask.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiResponseExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = RecordNotFoundException.class)
  private ResponseEntity<String> handleNotFound(RecordNotFoundException ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    String message = "Cannot handle request. Validation error: " + (ex.getFieldError() == null ? "" : ex.getFieldError().getDefaultMessage());
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }
}

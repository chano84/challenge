package com.tenpo.challenge.controller;

import com.tenpo.challenge.exceptions.BusinessException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {

    /**
     * Handler for a request with invalid data exception.
     *
     * @param ex The exception that caused the error
     * @return The HTTP response with the status and error message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> invalidRequestHandler(IllegalArgumentException ex) {
        return status(HttpStatus.BAD_REQUEST)
                .body("Invalid Request");
    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> businessExceptionHandler(BusinessException ex) {
        return status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Object> requestExceptionHandler(RequestNotPermitted ex) {
        return status(HttpStatus.TOO_MANY_REQUESTS)
                .body("To many request");
    }

    /**
     * Handler for a unexpected exception.
     *
     * @param ex The exception that caused the error.
     * @return The HTTP response with the status and error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> unexpectedExceptionHandler(Exception ex) {
        return status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected Error");
    }

}
package ru.alxdv.nfoshop.exception.handler;

import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;
import javax.validation.ValidationException;

@ControllerAdvice
public class NfoResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    protected ResponseEntity<Object> handleValidation(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception,
                "Validation error: "+exception.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value = FeignException.class)
    protected ResponseEntity<Object> handleFeign(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception,
                "Authorization server error: "+exception.getMessage(),
                new HttpHeaders(),
                HttpStatus.UNAUTHORIZED,
                request);
    }

    @ExceptionHandler(value = PersistenceException.class)
    protected ResponseEntity<Object> handlePersistence(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception,
                "Object persistence error: "+exception.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleOtherExceptions(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception,
                "Server error: "+exception.getMessage(),
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request);
    }
}

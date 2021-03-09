package ru.alxdv.nfoshop.exception.handler;

import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.alxdv.nfoshop.exception.NfoException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.validation.ValidationException;

@ControllerAdvice
public class NfoResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    protected ResponseEntity<Object> handleValidation(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception,
                generateErrorResponseEntity("Validation error!", exception.getMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return handleExceptionInternal(exception,
                generateErrorResponseEntity("Validation error!", exception.getMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value = FeignException.class)
    protected ResponseEntity<Object> handleFeign(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception,
                generateErrorResponseEntity("Authorization error!", exception.getMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception,
                generateErrorResponseEntity("Entity not found!", exception.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(value = EntityExistsException.class)
    protected ResponseEntity<Object> handleEntityExists(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception,
                generateErrorResponseEntity("Entity has already exist!", exception.getMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value = PersistenceException.class)
    protected ResponseEntity<Object> handlePersistence(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception,
                generateErrorResponseEntity("Persistence error!", exception.getMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleOtherExceptions(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception,
                generateErrorResponseEntity("Server error!", exception.getMessage()),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(value = NfoException.class)
    protected ResponseEntity<Object> handleNfoExceptions(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception,
                generateErrorResponseEntity("Business-logic exception!", exception.getMessage()),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    private ErrorResponseEntity generateErrorResponseEntity(String error, String cause){
        return ErrorResponseEntity.builder()
                .error(error)
                .cause(cause)
                .build();
    }
}

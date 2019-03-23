package com.cars.platform.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cars.datatransferobject.error.validation.ValidationError;

/**
 * Add a Global Validation Exception handler.
 * Handles Javax Validation messages and offer to the user in Error Info Wrapped class with validation message and correct HTTP Status.
 * Set it with highest precedence to put it up in the controller advise bean collections as it's exceptions is more concrete class
 * than GlobalExceptionHandler.
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ValidationExceptionHandler extends ResponseEntityExceptionHandler
{

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        final ValidationError validationError = new ValidationError();
        final BindingResult result = ex.getBindingResult();
        result.getFieldErrors().forEach(error -> validationError.addFieldError(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }
}

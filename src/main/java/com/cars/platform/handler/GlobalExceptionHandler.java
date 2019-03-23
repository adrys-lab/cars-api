package com.cars.platform.handler;

import com.cars.model.exception.external.ApiExternalException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cars.datatransferobject.error.ErrorInfo;
import com.cars.model.exception.external.BadRequestException;
import com.cars.model.exception.internal.ApiInternalException;

/**
 * Add a Global Exception handler to ensure there's no any Exception going out our platform.
 * We don't want the user to see any stacktrace or undesired exception message.
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{

    private static final String BAD_REQUEST = "Oops ! There's an error in your params, please have it a look and try again.";
    private static final String DEFAULT_ERROR_MSG = "Oops ! There's a fatal error in our platform. Try again later.";
    private static final String UNAUTHORIZED_MSG = "Sorry, user unauthorized.";

    /*
     * Handles unchecked and therefore critical exceptions
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> defaultErrorHandler(final WebRequest request, final Exception e) throws Exception
    {
        /*
         * If Exception is ApiExternalException is allowed to show it to the end-user.
         * ---> The criteria of the test requests to throw some exceptions to the end-user.
         */
        if (e instanceof ApiExternalException)
        {
            throw e;
        }
        return handleExceptionInternal(e, DEFAULT_ERROR_MSG, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /*
     * Handles Bad requests from user.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        return handleExceptionInternal(new BadRequestException(), BAD_REQUEST, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /*
     * Handles unauthorized attempts to API.
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {AccessDeniedException.class, BadCredentialsException.class})
    @ResponseBody ErrorInfo
    handleUnauthorized(final WebRequest request, final Exception e)
    {
        return new ErrorInfo(UNAUTHORIZED_MSG, HttpStatus.UNAUTHORIZED.value());
    }

    /*
     * Handles checked exceptions to our Customized Error Information Response.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ApiInternalException.class)
    @ResponseBody ErrorInfo
    handleCheckedException(final WebRequest request, final ApiInternalException e)
    {
        final ResponseStatus annotatedException = e.getClass().getAnnotation(ResponseStatus.class);
        return new ErrorInfo(annotatedException.reason(), e.getDetail(), annotatedException.code().value());
    }

}

package com.cars.model.exception.internal.driver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cars.model.exception.internal.ApiInternalException;

/*
* Data integrity violations for Drivers. related with username.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST, reason = "Error with Driver. Driver username has to be unique.")
public class DriverDataIntegrityViolationException extends ApiInternalException {
}

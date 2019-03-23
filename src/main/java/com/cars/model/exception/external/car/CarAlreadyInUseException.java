package com.cars.model.exception.external.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cars.model.exception.external.ApiExternalException;

/*
* Exception for cars which are already in use.
* The criteria of the test requests to show it to the end-user.
*/
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, code = HttpStatus.NOT_ACCEPTABLE, reason = "This Car is already in use by some Driver.")
public class CarAlreadyInUseException extends ApiExternalException
{
}

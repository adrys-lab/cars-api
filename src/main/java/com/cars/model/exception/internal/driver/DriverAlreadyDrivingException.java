package com.cars.model.exception.internal.driver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cars.model.exception.internal.ApiInternalException;

/*
* Exception for drivers which are already driving a car.
*/
@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST, reason = "This Driver is driving another Car, please, first leave current car.")
public class DriverAlreadyDrivingException extends ApiInternalException
{
}

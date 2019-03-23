package com.cars.model.exception.internal.driver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cars.model.exception.internal.ApiInternalException;

/*
* Exception for drivers which are not driving a car and try to leave car.
*/
@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST, reason = "This Driver is not driving any car.")
public class DriverIsNotDrivingException extends ApiInternalException
{
}

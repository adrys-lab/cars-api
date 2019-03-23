package com.cars.model.exception.internal.driver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cars.model.exception.internal.ApiInternalException;

/*
* Exception for Persistence exceptions with Drivers
* It contains exception information in ResponseStatus annotation
*/
@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST, reason = "An error occurred saving the driver. Please review all data.")
public class DriverPersistenceException extends ApiInternalException
{
}
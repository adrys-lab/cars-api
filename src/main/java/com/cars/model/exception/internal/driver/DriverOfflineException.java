package com.cars.model.exception.internal.driver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cars.model.exception.internal.ApiInternalException;

/*
* Exception for Drivers offline.
* It contains exception information in ResponseStatus annotation
*/
@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST, reason = "The choosen driver is Offline and can not use a car.")
public class DriverOfflineException extends ApiInternalException
{
}

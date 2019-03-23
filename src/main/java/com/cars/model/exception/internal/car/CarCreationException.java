package com.cars.model.exception.internal.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cars.model.exception.internal.ApiInternalException;

/*
* Exception for errors creating car.
* It contains exception information in ResponseStatus annotation
*/
@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST, reason = "An error occurred creating the car. Please review all data.")
public class CarCreationException extends ApiInternalException
{
}
package com.cars.model.exception.internal.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cars.model.exception.internal.ApiInternalException;

/*
* Exception for not cars found.
* It contains exception information in ResponseStatus annotation
*/
@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST, reason = "Car with this ID can not be found.")
public class CarNotFoundException extends ApiInternalException
{
}

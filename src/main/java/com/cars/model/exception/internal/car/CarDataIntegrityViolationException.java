package com.cars.model.exception.internal.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cars.model.exception.internal.ApiInternalException;

/*
 * Data integrity violations for Cars. related with manufacturer and licenseplate.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST, reason = "Error with Car. Manufacturer has to exist and Licenseplate has to be unique.")
public class CarDataIntegrityViolationException extends ApiInternalException
{
}

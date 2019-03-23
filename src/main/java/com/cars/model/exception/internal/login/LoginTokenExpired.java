package com.cars.model.exception.internal.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cars.model.exception.internal.ApiInternalException;

/*
* Exception for Expired Login Tokens.
* It contains exception information in ResponseStatus annotation
*/
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, code = HttpStatus.UNAUTHORIZED, reason = "The Login token has expired, please login again.")
public class LoginTokenExpired extends ApiInternalException
{
}

package com.cars.model.user;

/*
* Enum for our user roles.
* Remember Spring security framework assume they start by 'ROLE_'
*/
public enum UserRole {
    ROLE_READER, ROLE_WRITER, ROLE_UNAUTHORIZED
}
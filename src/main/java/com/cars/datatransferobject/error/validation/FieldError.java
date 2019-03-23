package com.cars.datatransferobject.error.validation;

import java.io.Serializable;

/**
* Field Error for Javax Error Validation Field
*/
class FieldError implements Serializable
{

    private String field;
    private String message;

    FieldError(String field, String message)
    {
        this.field = field;
        this.message = message;
    }

    public FieldError()
    {
    }

    public String getField()
    {
        return field;
    }

    public String getMessage()
    {
        return message;
    }
}
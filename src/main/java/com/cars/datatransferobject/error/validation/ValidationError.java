package com.cars.datatransferobject.error.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* DTO to Wrapp all Javax Error Validations
*/
public class ValidationError implements Serializable
{

    private List<FieldError> fieldErrors = new ArrayList<>();

    public ValidationError()
    {
    }

    public void addFieldError(String path, String message)
    {
        final FieldError error = new FieldError(path, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors()
    {
        return fieldErrors;
    }
}

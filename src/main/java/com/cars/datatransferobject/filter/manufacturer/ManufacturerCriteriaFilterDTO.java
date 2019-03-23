package com.cars.datatransferobject.filter.manufacturer;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.cars.datatransferobject.filter.CriteriaFilterDTO;
import com.cars.datatransferobject.filter.field.StringFilterField;

/*
* DTO Class to wrap Criteria to filter Manufacturers.
* It's embedded into CarCriteriaFilterDTO
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManufacturerCriteriaFilterDTO implements CriteriaFilterDTO, Serializable
{

    private static final long serialVersionUID = -92612390786234778L;

    private StringFilterField name;
    private StringFilterField description;
    private StringFilterField country;

    private ManufacturerCriteriaFilterDTO()
    {
    }

    public StringFilterField getName()
    {
        return name;
    }

    public StringFilterField getDescription()
    {
        return description;
    }

    public StringFilterField getCountry()
    {
        return country;
    }
}

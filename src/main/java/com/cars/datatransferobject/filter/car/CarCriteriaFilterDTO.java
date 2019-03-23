package com.cars.datatransferobject.filter.car;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.cars.datatransferobject.filter.CriteriaFilterDTO;
import com.cars.datatransferobject.filter.field.FilterField;
import com.cars.datatransferobject.filter.field.StringFilterField;
import com.cars.datatransferobject.filter.manufacturer.ManufacturerCriteriaFilterDTO;

/*
* DTO Class to wrap Criteria to filter Cars.
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarCriteriaFilterDTO implements CriteriaFilterDTO, Serializable
{

    private static final long serialVersionUID = -9265556846234778L;

    private StringFilterField licensePlate;
    private FilterField<Integer> seatCount;
    private FilterField<Boolean> convertible;
    private FilterField<Double> rating;
    private StringFilterField engineType;
    private ManufacturerCriteriaFilterDTO manufacturer;

    private CarCriteriaFilterDTO()
    {
    }

    public StringFilterField getLicensePlate()
    {
        return licensePlate;
    }

    public FilterField<Integer> getSeatCount()
    {
        return seatCount;
    }

    public FilterField<Boolean> getConvertible()
    {
        return convertible;
    }

    public FilterField<Double> getRating()
    {
        return rating;
    }

    public StringFilterField getEngineType()
    {
        return engineType;
    }

    public ManufacturerCriteriaFilterDTO getManufacturer()
    {
        return manufacturer;
    }
}

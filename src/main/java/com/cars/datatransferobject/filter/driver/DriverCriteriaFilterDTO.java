package com.cars.datatransferobject.filter.driver;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.cars.datatransferobject.filter.CriteriaFilterDTO;
import com.cars.datatransferobject.filter.field.FilterField;
import com.cars.datatransferobject.filter.field.StringFilterField;

/*
* DTO Class to wrap Criteria to filter Drivers.
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverCriteriaFilterDTO implements CriteriaFilterDTO, Serializable
{

    private static final long serialVersionUID = -9265556846234778L;

    private StringFilterField status;
    private FilterField<Boolean> deleted;

    private DriverCriteriaFilterDTO()
    {
    }

    public DriverCriteriaFilterDTO(final StringFilterField status, final FilterField<Boolean> deleted)
    {
        this.deleted = deleted;
        this.status = status;
    }

    public FilterField<Boolean> getDeleted()
    {
        return deleted;
    }

    public StringFilterField getStatus()
    {
        return status;
    }
}

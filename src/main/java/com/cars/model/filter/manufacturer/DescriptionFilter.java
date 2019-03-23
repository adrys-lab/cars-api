package com.cars.model.filter.manufacturer;

import com.cars.datatransferobject.filter.field.FilterField;
import com.cars.datatransferobject.filter.manufacturer.ManufacturerCriteriaFilterDTO;
import com.cars.model.filter.AbstractFilter;
import com.cars.model.filter.FilterResultWrapper;

/*
* Filter for Manufacturer.description
*/
class DescriptionFilter extends AbstractFilter<ManufacturerCriteriaFilterDTO>
{

    private static final String QUERY_FIELD = "description";

    DescriptionFilter()
    {
        super(QUERY_FIELD);
    }

    @Override
    protected FilterField getFilterField(final FilterResultWrapper<ManufacturerCriteriaFilterDTO> filterResultWrapper)
    {
        return filterResultWrapper.getCriteriaFilter().getDescription();
    }
}

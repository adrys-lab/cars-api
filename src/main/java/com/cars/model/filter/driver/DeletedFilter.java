package com.cars.model.filter.driver;

import com.cars.datatransferobject.filter.field.FilterField;
import com.cars.datatransferobject.filter.driver.DriverCriteriaFilterDTO;
import com.cars.model.filter.AbstractFilter;
import com.cars.model.filter.FilterResultWrapper;

/*
* Filter for driver.deleted
*/
class DeletedFilter extends AbstractFilter<DriverCriteriaFilterDTO>
{

    private static final String QUERY_FIELD = "deleted";

    DeletedFilter()
    {
        super(QUERY_FIELD);
    }

    @Override
    protected FilterField getFilterField(final FilterResultWrapper<DriverCriteriaFilterDTO> filterResultWrapper)
    {
        return filterResultWrapper.getCriteriaFilter().getDeleted();
    }
}

package com.cars.model.filter.driver;

import com.cars.datatransferobject.filter.field.FilterField;
import com.cars.datatransferobject.filter.driver.DriverCriteriaFilterDTO;
import com.cars.model.filter.AbstractFilter;
import com.cars.model.filter.FilterResultWrapper;

/*
* Filter for driver.status(ONLINE, OFFLINE)
*/
class StatusFilter extends AbstractFilter<DriverCriteriaFilterDTO>
{

    private static final String QUERY_FIELD = "online_status";

    StatusFilter()
    {
        super(QUERY_FIELD);
    }

    @Override
    protected FilterField getFilterField(final FilterResultWrapper<DriverCriteriaFilterDTO> filterResultWrapper)
    {
        return filterResultWrapper.getCriteriaFilter().getStatus();
    }
}

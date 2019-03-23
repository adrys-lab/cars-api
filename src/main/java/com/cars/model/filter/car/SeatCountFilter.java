package com.cars.model.filter.car;

import com.cars.datatransferobject.filter.car.CarCriteriaFilterDTO;
import com.cars.datatransferobject.filter.field.FilterField;
import com.cars.model.filter.AbstractFilter;
import com.cars.model.filter.FilterResultWrapper;

/*
* Filter for car.seatCount
*/
class SeatCountFilter extends AbstractFilter<CarCriteriaFilterDTO>
{

    private static final String QUERY_FIELD = "seat_count";

    SeatCountFilter()
    {
        super(QUERY_FIELD);
    }

    @Override
    protected FilterField getFilterField(final FilterResultWrapper<CarCriteriaFilterDTO> filterResultWrapper)
    {
        return filterResultWrapper.getCriteriaFilter().getSeatCount();
    }
}

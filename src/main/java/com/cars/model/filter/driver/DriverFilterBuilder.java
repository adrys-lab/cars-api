package com.cars.model.filter.driver;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.cars.datatransferobject.filter.car.CarCriteriaFilterDTO;
import com.cars.datatransferobject.filter.driver.DriverCriteriaFilterDTO;
import com.cars.model.filter.AbstractFilterBuilder;
import com.cars.model.filter.car.CarFilterBuilder;

/*
* Filter Builder  apply DriverFilter.
* It embeds Car Filter Builder.
* By default it applies Status and Deleted Filters.
*/
public class DriverFilterBuilder extends AbstractFilterBuilder<DriverCriteriaFilterDTO>
{

    private CarFilterBuilder carFilterBuilder;

    public DriverFilterBuilder(final DriverCriteriaFilterDTO driverCriteriaFilterDTO)
    {
        super(driverCriteriaFilterDTO);
    }

    public DriverFilterBuilder withCarCriteria(final CarCriteriaFilterDTO carCriteriaFilterDTO)
    {
        this.carFilterBuilder = new CarFilterBuilder(carCriteriaFilterDTO);
        return this;
    }

    public DriverFilterBuilder withDriverTableAlias(final String tableAlias)
    {
        super.withTableAlias(tableAlias);
        return this;
    }

    public DriverFilterBuilder withCarTableAlias(final String tableAlias)
    {
        carFilterBuilder.withTableAlias(tableAlias);
        return this;
    }

    public DriverFilterBuilder withManufacturerTableAlias(final String tableAlias)
    {
        carFilterBuilder.getManufacturerFilterBuilder().withTableAlias(tableAlias);
        return this;
    }

    public DriverFilterBuilder addAllFilters()
    {
        super.addFilters(Arrays.asList(new StatusFilter(),
                new DeletedFilter()));

        carFilterBuilder.addAllFilters();
        return this;
    }

    public String build()
    {
        final String driverQuery = super.build();
        final String carQuery = carFilterBuilder.build();
        return StringUtils.isNoneBlank(driverQuery, carQuery) ? driverQuery + " AND " + carQuery : driverQuery + carQuery;
    }
}

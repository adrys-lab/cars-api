package com.cars.model.filter.car;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.cars.datatransferobject.filter.car.CarCriteriaFilterDTO;
import com.cars.model.filter.AbstractFilterBuilder;
import com.cars.model.filter.manufacturer.ManufacturerFilterBuilder;

/*
* Filter Builder  apply CarFilter.
* It embeds Manufacturer Builder cause Car contains Manufacturer.
* however, each Filter builder can perfectly work independently.
* As i understand, a car filtering involves also manufacturer (if no manufacturer data is provided then it won't be treated).
* By default it applies LicensePlate, Convertible, RatingFilter, SeatCount and Engine Filters.
*/
public class CarFilterBuilder extends AbstractFilterBuilder<CarCriteriaFilterDTO>
{

    private final ManufacturerFilterBuilder manufacturerFilterBuilder;

    public CarFilterBuilder(final CarCriteriaFilterDTO carCriteriaFilterDTO)
    {
        super(carCriteriaFilterDTO);
        this.manufacturerFilterBuilder = new ManufacturerFilterBuilder(getCriteriaFilter().getManufacturer());
    }

    public CarFilterBuilder withCarTableAlias(final String tableAlias)
    {
        return (CarFilterBuilder) super.withTableAlias(tableAlias);
    }

    public CarFilterBuilder withManufacturerTableAlias(final String tableAlias)
    {
        manufacturerFilterBuilder.withTableAlias(tableAlias);
        return this;
    }

    public CarFilterBuilder addAllFilters()
    {
        super.addFilters(Arrays.asList(new LicenseFilter(),
                new ConvertibleFilter(),
                new RatingFilter(),
                new SeatCountFilter(),
                new EngineFilter()));

        manufacturerFilterBuilder.addAllFilters();

        return this;
    }

    public String build()
    {
        final String carQuery = super.build();
        final String manufacturerQuery = manufacturerFilterBuilder.build();
        return StringUtils.isNoneBlank(carQuery, manufacturerQuery) ? carQuery + " AND " + manufacturerQuery : carQuery + manufacturerQuery;
    }

    public ManufacturerFilterBuilder getManufacturerFilterBuilder()
    {
        return manufacturerFilterBuilder;
    }
}

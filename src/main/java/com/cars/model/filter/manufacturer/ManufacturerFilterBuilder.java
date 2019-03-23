package com.cars.model.filter.manufacturer;

import java.util.Arrays;

import com.cars.datatransferobject.filter.manufacturer.ManufacturerCriteriaFilterDTO;
import com.cars.model.filter.AbstractFilterBuilder;

/*
* Filter Builder applied for Manufacturer.
* By default it applies Name, Description and Country Filters.
*/
public class ManufacturerFilterBuilder extends AbstractFilterBuilder<ManufacturerCriteriaFilterDTO>
{

    public ManufacturerFilterBuilder(final ManufacturerCriteriaFilterDTO manufacturerCriteriaFilterDTO)
    {
        super(manufacturerCriteriaFilterDTO);
    }

    public ManufacturerFilterBuilder withTableAlias(final String tableAlias)
    {
        super.withTableAlias(tableAlias);
        return this;
    }

    public ManufacturerFilterBuilder addAllFilters()
    {
        super.addFilters(Arrays.asList(new NameFilter(),
                new DescriptionFilter(),
                new CountryFilter()));

        return this;
    }
}

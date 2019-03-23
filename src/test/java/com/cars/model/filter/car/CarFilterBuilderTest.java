package com.cars.model.filter.car;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cars.CommonTest;
import com.cars.datatransferobject.filter.car.CarCriteriaFilterDTO;
import com.cars.datatransferobject.filter.field.Condition;
import com.cars.datatransferobject.filter.field.FilterField;
import com.cars.datatransferobject.filter.field.StringFilterField;
import com.cars.model.filter.AbstractFilter;
import com.cars.model.filter.FilterResultWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
public class CarFilterBuilderTest extends CommonTest
{

    private static final String FULL_CRITERIA_FILTER_DTO_JSON = "/carCriteriaFilterDTO.json";
    private File fullCriteriaFilterDTO;

    private static final String MANUFACTURER_CRITERIA_FILTER_DTO_JSON = "/manufacturerCriteriaFilterDTO.json";
    private File manufacturerCriteriaFilterDTO;

    private CarCriteriaFilterDTO carCriteriaFilterDTO;
    private CarFilterBuilder carFilterBuilder;

    private static final List<String> ALL_CAR_FIELDS = Arrays.asList("license_plate", "convertible", "rating", "seat_count", "engine_type");
    private static final List<String> ALL_MANUFACTURER_FIELDS = Arrays.asList("country", "description", "name");

    @Before
    public void setUp() throws Exception
    {
        fullCriteriaFilterDTO = new File(this.getClass().getResource(FULL_CRITERIA_FILTER_DTO_JSON).getFile());
        manufacturerCriteriaFilterDTO = new File(this.getClass().getResource(MANUFACTURER_CRITERIA_FILTER_DTO_JSON).getFile());
        carCriteriaFilterDTO = modelifyJson(fullCriteriaFilterDTO, CarCriteriaFilterDTO.class);
        carFilterBuilder = new CarFilterBuilder(carCriteriaFilterDTO);
    }

    @Test
    public void builderWithNoFiltersNotBreaks() throws Exception
    {
        Assert.assertTrue(StringUtils.isEmpty(carFilterBuilder.build()));
    }

    /*
     * 2 Filters of same class are not executed -> only the last added.
     */
    @Test
    public void testAvoidFilterDuplication() throws Exception {
        final String firstFilter = "dummy1";
        final String secondFilter = "dummy2";
        carFilterBuilder.addFilter(new DummyFilter(firstFilter)).addFilter(new DummyFilter(secondFilter));
        final String result = carFilterBuilder.build();
        Assert.assertFalse(StringUtils.contains(result, firstFilter));
        Assert.assertTrue(StringUtils.contains(result, secondFilter));
    }

    @Test
    public void allTablesArePresentWithAllFilters() throws Exception
    {
        final String result = carFilterBuilder
                .addAllFilters()
                .build();
        ALL_CAR_FIELDS.forEach(column -> Assert.assertTrue(result.contains(column)));
        ALL_MANUFACTURER_FIELDS.forEach(column -> Assert.assertTrue(result.contains(column)));
    }

    @Test
    public void allTablesArePresentWithCarAliasAllFilters() throws Exception
    {
        final String tableAlias = "table";
        final String result = carFilterBuilder
                .addAllFilters()
                .withCarTableAlias(tableAlias)
                .build();
        ALL_CAR_FIELDS.forEach(column -> Assert.assertTrue(result.contains(tableAlias + "." + column)));
        ALL_MANUFACTURER_FIELDS.forEach(column -> Assert.assertFalse(result.contains(tableAlias + "." + column)));
    }

    @Test
    public void allTablesArePresentWithManufacturerAliasAllFilters() throws Exception
    {
        final String tableAlias = "table";
        final String result = carFilterBuilder
                .addAllFilters()
                .withManufacturerTableAlias(tableAlias)
                .build();
        ALL_CAR_FIELDS.forEach(column -> Assert.assertFalse(result.contains(tableAlias + "." + column)));
        ALL_MANUFACTURER_FIELDS.forEach(column -> Assert.assertTrue(result.contains(tableAlias + "." + column)));
    }

    @Test
    public void allTablesArePresentWithBothAliasAllFilters() throws Exception
    {
        final String carTableAlias = "carTableAlias";
        final String manufacturertableAlias = "manufacturertableAlias";
        final String result = carFilterBuilder
                .addAllFilters()
                .withCarTableAlias(carTableAlias)
                .withManufacturerTableAlias(manufacturertableAlias)
                .build();
        ALL_CAR_FIELDS.forEach(column -> Assert.assertTrue(result.contains(carTableAlias + "." + column)));
        ALL_MANUFACTURER_FIELDS.forEach(column -> Assert.assertTrue(result.contains(manufacturertableAlias + "." + column)));
    }

    @Test
    public void checkFullQuery() throws Exception
    {
        final String result = carFilterBuilder
                .addAllFilters()
                .build();
        Assert.assertTrue(result.contains(carCriteriaFilterDTO.getLicensePlate().getCondition().getQueryfied() + " " + carCriteriaFilterDTO.getLicensePlate().getQuerifiedValue()));
        Assert.assertTrue(result.contains(carCriteriaFilterDTO.getEngineType().getCondition().getQueryfied() + " " + carCriteriaFilterDTO.getEngineType().getQuerifiedValue()));
        Assert.assertTrue(result.contains(carCriteriaFilterDTO.getRating().getCondition().getQueryfied() + " " + carCriteriaFilterDTO.getRating().getValue()));
        Assert.assertTrue(result.contains(carCriteriaFilterDTO.getSeatCount().getCondition().getQueryfied() + " " + carCriteriaFilterDTO.getSeatCount().getValue()));
        Assert.assertTrue(result.contains(carCriteriaFilterDTO.getConvertible().getCondition().getQueryfied() + " " + carCriteriaFilterDTO.getConvertible().getValue()));
    }

    @Test
    public void checkOnlyManufacturer() throws Exception
    {
        carCriteriaFilterDTO = modelifyJson(manufacturerCriteriaFilterDTO, CarCriteriaFilterDTO.class);
        carFilterBuilder = new CarFilterBuilder(carCriteriaFilterDTO);
        final String result = carFilterBuilder
                .addAllFilters()
                .build();
        ALL_CAR_FIELDS.forEach(column -> Assert.assertFalse(result.contains(column)));
        ALL_MANUFACTURER_FIELDS.forEach(column -> Assert.assertTrue(result.contains(column)));
    }

    private class DummyFilter extends AbstractFilter<CarCriteriaFilterDTO>
    {
        final String value;

        DummyFilter(final String value)
        {
            super(value);
            this.value = value;
        }
        @Override
        protected FilterField getFilterField(FilterResultWrapper<CarCriteriaFilterDTO> filterResultWrapper)
        {
            return new StringFilterField(Condition.EQUALS, value);
        }
    }

}

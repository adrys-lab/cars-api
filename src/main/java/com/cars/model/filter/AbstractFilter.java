package com.cars.model.filter;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.cars.datatransferobject.filter.CriteriaFilterDTO;
import com.cars.datatransferobject.filter.field.FilterField;

/*
* Abstract Filter, super-class of all Filters, with the common pattern to Apply the Filtering.
* Implements Filter Functional Interface.
* It's Generics are the Wrapper typed to the criteria which it Treats
*/
public abstract class AbstractFilter<T extends CriteriaFilterDTO> implements Filter<T>
{

    //Base query to map ex: 'license = 4' {%s %s %s}
    private static final String BASE_QUERY_MAP = "%s %s %s";
    private static final String DOT = ".";
    private static final String SPACE = " ";

    private final String queryField;

    public AbstractFilter(String queryField)
    {
        this.queryField = queryField;
    }

    public void apply(final FilterResultWrapper<T> filterResultWrapper)
    {
        if (passCriteria(filterResultWrapper))
        {
             filterResultWrapper.appendChain(querify(filterResultWrapper));
        }
    }

    private String querify(final FilterResultWrapper<T> filterResultWrapper)
    {
        return StringUtils.isBlank(filterResultWrapper.getTableAlias()) ?
                SPACE.concat(mapQuery(filterResultWrapper)) :
                    SPACE.concat(filterResultWrapper.getTableAlias().concat(DOT).concat(mapQuery(filterResultWrapper)));
    }

    private boolean passCriteria(final FilterResultWrapper<T> filterResultWrapper)
    {
        return Objects.nonNull(getFilterField(filterResultWrapper))
                && Objects.nonNull(getFilterField(filterResultWrapper).getCondition())
                && Objects.nonNull(getFilterField(filterResultWrapper).getValue());
    }

    private String mapQuery(final FilterResultWrapper<T> filterResultWrapper)
    {
            return String.format(BASE_QUERY_MAP, queryField, getFilterField(filterResultWrapper).getCondition().getQueryfied(), getFilterField(filterResultWrapper).getQuerifiedValue());
    }

    protected abstract FilterField getFilterField(FilterResultWrapper<T> filterResultWrapper);
}

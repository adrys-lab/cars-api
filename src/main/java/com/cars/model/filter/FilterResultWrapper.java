package com.cars.model.filter;

import org.apache.commons.lang3.StringUtils;

import com.cars.datatransferobject.filter.CriteriaFilterDTO;

/*
* Filter result which wrapps the information relative to the Result.
* It contains the filter filterChainResult passed overall the filters.
* The final Filter result is wrapped in this Result Wrapper class.
*/
public class FilterResultWrapper<T extends CriteriaFilterDTO>
{

    private static final String AND = " AND ";

    private String filterChainResult = StringUtils.EMPTY;

    protected final T t;
    private final String tableAlias;


    protected FilterResultWrapper(T t, final String tableAlias)
    {
        this.t = t;
        this.tableAlias = StringUtils.defaultIfEmpty(tableAlias, StringUtils.EMPTY);
    }

    /*
    * Table alias used for the final query -> should be appended before each comparison/field/filter.
    */
    String getTableAlias()
    {
        return tableAlias;
    }

    String getFilterChainResult()
    {
        return filterChainResult;
    }

    /*
    * gets the initial criteria filter (car, driver, manufacturer) -> sent by user.
    */
    public T getCriteriaFilter()
    {
        return t;
    }

    /*
    * Append the sentence.
    * checks if has content to add the ' AND ' clause.
    */
    FilterResultWrapper appendChain(final String result)
    {
        filterChainResult = filterChainResult.concat(StringUtils.isBlank(filterChainResult) ? result :  AND.concat(result));
        return this;
    }
}

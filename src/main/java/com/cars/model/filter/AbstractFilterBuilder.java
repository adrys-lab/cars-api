package com.cars.model.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.cars.datatransferobject.filter.CriteriaFilterDTO;

/*
* Abstract Filter Builder With common methods to build Filter Chain and Filter logic.
* All Concrete Filters (Car & Driver) should inherit it.
*/
public abstract class AbstractFilterBuilder<T extends CriteriaFilterDTO>
{

    /*
    * Use Map with Filter class key -> avoid duplicate filters.
    */
    private Map<Class, Filter<T>> filterChain = new HashMap<>();
    private final T t;
    private String tableAlias;

    protected AbstractFilterBuilder(final T t)
    {
        this.t = t;
    }

    public AbstractFilterBuilder<T> addFilter(final Filter<T> filter)
    {

        this.filterChain.put(filter.getClass(), filter);
        return this;
    }

    public AbstractFilterBuilder<T> withTableAlias(final String tableAlias)
    {
        this.tableAlias = tableAlias;
        return this;
    }

    public String build()
    {
        if(Objects.nonNull(getCriteriaFilter()))
        {
            return applyFilterChain().getFilterChainResult();
        }
        return StringUtils.EMPTY;
    }

    private FilterResultWrapper<T> applyFilterChain()
    {
        final FilterResultWrapper<T> filterResultWrapper = createResultWrapper();
        if(!filterChain.isEmpty())
        {
            filterChain.entrySet().forEach(filter -> filter.getValue().apply(filterResultWrapper));
        }
        return filterResultWrapper;
    }

    private String getTableAlias()
    {
        return tableAlias;
    }

    protected final void addFilters(final List<Filter<T>> filters)
    {
        filters.forEach(this::addFilter);
    }

    protected T getCriteriaFilter()
    {
        return t;
    }

    private FilterResultWrapper<T> createResultWrapper()
    {
        return new FilterResultWrapper<>(getCriteriaFilter(), getTableAlias());
    }

}

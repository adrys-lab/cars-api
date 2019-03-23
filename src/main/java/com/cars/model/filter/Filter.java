package com.cars.model.filter;

import com.cars.datatransferobject.filter.CriteriaFilterDTO;

/*
* Functional Filter interface with one abstract method.
* Its Generics are for the Criteria filter wrapped into FilterResultWrapper which is applied to.
* All Filters should implement this interface.
*/
@FunctionalInterface
public interface Filter<T extends CriteriaFilterDTO> {

    void apply(final FilterResultWrapper<T> filterResultWrapper);
}

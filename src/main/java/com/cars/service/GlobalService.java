package com.cars.service;

import com.cars.domainobject.DomainObject;

/**
 * Global interface with common used methods in all sub-Services
 */
public interface GlobalService<T extends DomainObject>
{
    T find(final Long id);

    Iterable<T> findAll();

    T create(final T t);

    T update(final Long id, final T t);

    T delete(final T t);
}

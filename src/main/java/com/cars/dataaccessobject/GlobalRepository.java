package com.cars.dataaccessobject;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import com.cars.domainobject.DomainObject;

/**
 * Global repository inherited by all repositories.
 * Generics for related DomainObject (CarDO, DriverDO...) which are the entities for CrudRepository.
 */
@Component
@NoRepositoryBean
public interface GlobalRepository<T extends DomainObject> extends CrudRepository<T, Long>
{
}

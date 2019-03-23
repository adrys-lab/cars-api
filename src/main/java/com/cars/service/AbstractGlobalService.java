package com.cars.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cars.dataaccessobject.GlobalRepository;
import com.cars.domainobject.DomainObject;

/**
 * Global Service for Services.
 * - Cacheable Services should extend this AbstractCacheableGlobalService.
 * - Services NOT cacheable should extend directly AbstractGlobalService
 */
public abstract class AbstractGlobalService<T extends DomainObject> implements GlobalService<T>
{

    /**
     * Add a cache flow to avoid BBDD overload
     */
    @Autowired
    private EntityManager entityManager;

    public T find(final Long id)
    {
        final T t = getRepository().findOne(id);
        if (t == null)
        {
            throw new EntityNotFoundException(String.format("Could not find entity %s with id: %s", getDomainObjectClassName(), id));
        }
        return t;
    }

    public Iterable<T> findAll()
    {
        return getRepository().findAll();
    }

    @Transactional
    public T create(final T t)
    {
        return getRepository().save(t);
    }

    @Transactional
    public T update(final Long id, final T t)
    {
        t.setId(id);
        final T result = entityManager.merge(t);
        entityManager.flush();
        return result;
    }

    /*
     * No need for transactional process cause it relies the Transaction on the update method (which is Transactional).
     * ------- Doubt -------
     *      I saw in DefaultDriverService logicalDelete was logical (no phisical logicalDelete from DDBB).
     *      I followed on with this approach, even though i'm not 100% agree with this method.
     *      In addition, no logicalDelete check is done in the whole application so deleted drivers can be chosen for cars etc.
     * ---------------------
     */
    protected T logicalDelete(final Long id)
    {
        final T t = find(id);
        t.setDeleted(true);
        return update(t.getId(), t);
    }

    @Transactional
    protected T physicalDelete(final T t)
    {
        entityManager.remove(t);
        entityManager.flush();
        return t;
    }

    protected EntityManager getEntityManager()
    {
        return entityManager;
    }

    protected abstract GlobalRepository<T> getRepository();
    /**
     * Get cache names depending on children service
     */
    protected abstract String getDomainObjectClassName();
}

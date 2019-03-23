package com.cars.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

import com.cars.domainobject.DomainObject;

/**
 * Global Service for Services which need improvement of cache.
 * - Cacheable Services should extend this AbstractCacheableGlobalService.
 * - Services NOT cacheable should extend directly AbstractGlobalService
 * Manual management of Caches --> could not be done through Annotation cause it's dependant on the sub-service and.
 * Cache annotations could not be configured through variable cacheName.
 */
public abstract class AbstractCacheableGlobalService<T extends DomainObject> extends AbstractGlobalService<T>
{

    /**
     * Add a cache flow to avoid BBDD overload
     */
    @Autowired
    private CacheManager cacheManager;

    public T find(final Long id)
    {
        final T cacheElement = cacheGet(id);
        if (cacheElement == null)
        {
            final T t = super.find(id);
            return cachePut(t);
        } else {
            return cacheElement;
        }
    }

    public T create(final T t)
    {
        final T elementSaved = super.create(t);
        return cachePut(elementSaved);
    }

    public T update(final Long id, final T t)
    {
        final T result = super.update(id, t);
        return cachePut(result);
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
        super.physicalDelete(t);
        cacheDelete(t.getId());
        return t;
    }

    private T cachePut(T t)
    {
        getCache().put(t.getId(), t);
        return t;
    }

    private void cacheDelete(Long id)
    {
        getCache().evict(id);
    }


    @SuppressWarnings(value = "unchecked")
    private T cacheGet(final Long id)
    {
        final Cache.ValueWrapper cacheValue = getCache().get(id);
        return (T) Optional.ofNullable(cacheValue).map(Cache.ValueWrapper::get).orElse(null);
    }

    private Cache getCache()
    {
        return cacheManager.getCache(getCacheName());
    }

    protected abstract String getCacheName();
}

package com.cars.service.driver;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cars.dataaccessobject.DriverRepository;
import com.cars.datatransferobject.filter.car.CarCriteriaFilterDTO;
import com.cars.datatransferobject.filter.driver.DriverCriteriaFilterDTO;
import com.cars.domainobject.car.UseCarDO;
import com.cars.domainobject.driver.DriverDO;
import com.cars.domainvalue.GeoCoordinate;
import com.cars.domainvalue.OnlineStatus;
import com.cars.model.exception.internal.driver.DriverCreationException;
import com.cars.model.exception.internal.driver.DriverDataIntegrityViolationException;
import com.cars.model.exception.internal.driver.DriverNotFoundException;
import com.cars.model.exception.internal.driver.DriverPersistenceException;
import com.cars.model.filter.driver.DriverFilterBuilder;
import com.cars.service.AbstractCacheableGlobalService;


/**
 * Service for cars.
 * Override the methods just to catch exceptions and mapp them properly.
 */
@Service
class DefaultDriverService extends AbstractCacheableGlobalService<DriverDO> implements DriverService
{

    private static Logger LOGGER = LoggerFactory.getLogger(DefaultDriverService.class);

    private static final String CACHE_NAME = "drivers";

    @Autowired
    private  DriverRepository driverRepository;

    @Override
    public DriverDO find(final Long id)
    {
        try {
            return super.find(id);
        } catch (EntityNotFoundException e)
        {
            throw new DriverNotFoundException();
        }
    }

    @Override
    public DriverDO create(final DriverDO driverDO) {
        try {
            return super.create(driverDO);
        } catch (DataIntegrityViolationException | ConstraintViolationException e)
        {
            throw new DriverDataIntegrityViolationException();
        } catch (PersistenceException | HibernateException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new DriverDataIntegrityViolationException();
            } else {
                throw new DriverCreationException();
            }
        }
    }

    /**
     * Update the location for a driver.
     *
     * @param driverId driver identification
     * @param longitude longitude
     * @param latitude latitude
     * @throws EntityNotFoundException entityNotFoundException
     */
    @Override
    public void updateLocation(long driverId, double longitude, double latitude)
            throws EntityNotFoundException, DataIntegrityViolationException
    {
        LOGGER.info(String.format("Updating driver %s to set Location %s-%s", driverId, longitude, latitude));
        final DriverDO driverDO = find(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
        update(driverId, driverDO);
    }

    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus driver status
     */
    @Override
    @Cacheable(value = "drivers")
    public List<DriverDO> findByStatus(final OnlineStatus onlineStatus)
    {
        LOGGER.info(String.format("Retrieving drivers by Online Status %s", onlineStatus));
        return driverRepository.findByOnlineStatus(onlineStatus);
    }

    @Override
    public List<UseCarDO> filter(final DriverCriteriaFilterDTO driverCriteriaFilterDTO, final CarCriteriaFilterDTO carCriteriaFilterDTO)
    {
        final String driverFilter = new DriverFilterBuilder(driverCriteriaFilterDTO)
                .withCarCriteria(carCriteriaFilterDTO)
                .addAllFilters()
                .withDriverTableAlias("D")
                .withCarTableAlias("C")
                .withManufacturerTableAlias("M")
                .build();

        final String whereClause = StringUtils.isNotBlank(driverFilter) ? " AND " + driverFilter : StringUtils.EMPTY;
        Query query =
                getEntityManager().createNativeQuery(
                        "SELECT * FROM driver D, car C, manufacturer M, use_car U "
                                + "WHERE U.car = C.id AND U.driver = D.id AND C.manufacturer = M.id " + whereClause, UseCarDO.class);

        return query.getResultList();
    }

    /**
     * each sub-service choose if it's a phisical logicalDelete or a logical one.
     * Driver deletes logically
     */
    @Override
    @Transactional
    public DriverDO update(final Long id, final DriverDO driverDO)
    {
        try {
            return super.update(id, driverDO);
        } catch (DataIntegrityViolationException | ConstraintViolationException e)
        {
            throw new DriverDataIntegrityViolationException();
        } catch (PersistenceException | HibernateException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new DriverDataIntegrityViolationException();
            } else {
                throw new DriverPersistenceException();
            }
        }
    }

    @Override
    @Transactional
    public DriverDO delete(final DriverDO driverDO)
    {
        try {
            return super.logicalDelete(driverDO.getId());
        } catch (PersistenceException | HibernateException | DataAccessException e)
        {
            throw new DriverPersistenceException();
        }
    }

    @Override
    protected DriverRepository getRepository()
    {
        return driverRepository;
    }

    protected String getCacheName()
    {
        return CACHE_NAME;
    }

    protected String getDomainObjectClassName()
    {
        return DriverDO.class.getSimpleName();
    }
}

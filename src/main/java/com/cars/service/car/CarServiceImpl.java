package com.cars.service.car;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cars.controller.mapper.CarMapper;
import com.cars.dataaccessobject.CarRepository;
import com.cars.datatransferobject.car.UpdateCarDTO;
import com.cars.domainobject.car.CarDO;
import com.cars.model.exception.internal.car.CarCreationException;
import com.cars.model.exception.internal.car.CarDataIntegrityViolationException;
import com.cars.model.exception.internal.car.CarNotFoundException;
import com.cars.model.exception.internal.car.CarPersistenceException;
import com.cars.service.AbstractCacheableGlobalService;

/**
 * Service for cars.
 * Override the methods just to catch exceptions and mapp them properly
 */
@Service
class CarServiceImpl extends AbstractCacheableGlobalService<CarDO> implements CarService
{
    private static final String CACHE_NAME = "cars";

    @Autowired
    private CarRepository carRepository;

    @Override
    public CarDO find(final Long id)
    {
        try {
            return super.find(id);
        } catch (EntityNotFoundException e)
        {
            throw new CarNotFoundException();
        }
    }

    @Override
    public CarDO create(final CarDO carDO) {
        try {
            return super.create(carDO);
        } catch (DataIntegrityViolationException | ConstraintViolationException e)
        {
            throw new CarDataIntegrityViolationException();
        } catch (PersistenceException | HibernateException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new CarDataIntegrityViolationException();
            } else {
                throw new CarCreationException();
            }
        }
    }

    @Override
    @Transactional
    public CarDO update(final Long id, final CarDO carDO)
    {
        try {
            return super.update(id, carDO);
        } catch (DataIntegrityViolationException | ConstraintViolationException e)
        {
            throw new CarDataIntegrityViolationException();
        } catch (PersistenceException | HibernateException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new CarDataIntegrityViolationException();
            } else {
                throw new CarPersistenceException();
            }
        }
    }

    @Transactional
    public CarDO updateSome(final long carId, final UpdateCarDTO updateCarDTO)
    {
        final CarDO existingCarDO = find(carId);
        final CarDO updatedCarDO = CarMapper.merge(existingCarDO, updateCarDTO);
        return this.update(carId, updatedCarDO);
    }

    /**
     * each sub-service choose if it's a physical delete or a logical one.
     * Car deletes logically -> based on what Driver was doing.
     * It will depend in the business rules/restrictions.
     */
    @Override
    @Transactional
    public CarDO delete(final CarDO carDO)
    {
        try {
            return super.logicalDelete(carDO.getId());
        } catch (PersistenceException | HibernateException | DataAccessException e)
        {
            throw new CarPersistenceException();
        }
    }

    @Override
    protected CarRepository getRepository()
    {
        return carRepository;
    }

    @Override
    protected String getCacheName()
    {
        return CACHE_NAME;
    }

    @Override
    protected String getDomainObjectClassName()
    {
        return CarDO.class.getSimpleName();
    }
}

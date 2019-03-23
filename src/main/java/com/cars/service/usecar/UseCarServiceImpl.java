package com.cars.service.usecar;

import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cars.dataaccessobject.UseCarRepository;
import com.cars.domainobject.car.CarDO;
import com.cars.domainobject.car.UseCarDO;
import com.cars.domainobject.driver.DriverDO;
import com.cars.model.exception.internal.driver.DriverPersistenceException;
import com.cars.service.AbstractGlobalService;

/**
 * Service implementing Spring Security User Details Service to retrieve users.
 */
@Service
public class UseCarServiceImpl extends AbstractGlobalService<UseCarDO> implements UseCarService
{

    @Autowired
    private UseCarRepository useCarRepository;

    /**
     * each sub-service choose if it's a phisical logicalDelete or a logical one.
     * Use Car deletes phisically from DDBB
     */
    @Override
    @Transactional
    public UseCarDO delete(final UseCarDO useCarDO)
    {
        try {
            return super.physicalDelete(useCarDO);
        } catch (PersistenceException | HibernateException | DataAccessException e)
        {
            throw new DriverPersistenceException();
        }
    }

    @Override
    public UseCarDO findByCar(CarDO carDo)
    {
        return useCarRepository.findByCar(carDo);
    }

    @Override
    public UseCarDO findByDriver(DriverDO driverDO)
    {
        return useCarRepository.findByDriver(driverDO);
    }

    @Override
    protected UseCarRepository getRepository()
    {
        return useCarRepository;
    }

    @Override
    protected
    String getDomainObjectClassName()
    {
        return UseCarServiceImpl.class.getSimpleName();
    }
}
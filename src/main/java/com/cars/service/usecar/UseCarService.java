package com.cars.service.usecar;

import com.cars.domainobject.car.CarDO;
import com.cars.domainobject.car.UseCarDO;
import com.cars.domainobject.driver.DriverDO;
import com.cars.service.GlobalService;

/**
 * Interface for Use Car Service typed to It's domain object -> UseCarDO.
 * No methods as all needed are in Global.
 */
public interface UseCarService extends GlobalService<UseCarDO>
{
    UseCarDO findByCar(final CarDO car);
    UseCarDO findByDriver(final DriverDO driver);
}

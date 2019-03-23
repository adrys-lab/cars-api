package com.cars.dataaccessobject;

import org.springframework.stereotype.Repository;

import com.cars.domainobject.car.CarDO;
import com.cars.domainobject.car.UseCarDO;
import com.cars.domainobject.driver.DriverDO;

@Repository
public interface UseCarRepository extends GlobalRepository<UseCarDO>
{
    UseCarDO findByCar(final CarDO car);
    UseCarDO findByDriver(final DriverDO driver);
}

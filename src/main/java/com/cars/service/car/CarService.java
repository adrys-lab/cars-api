package com.cars.service.car;

import com.cars.datatransferobject.car.UpdateCarDTO;
import com.cars.domainobject.car.CarDO;
import com.cars.service.GlobalService;

/**
 * Interface for Car Service typed to It's domain object -> CarDO.
 */
public interface CarService extends GlobalService<CarDO>
{
    CarDO updateSome(final long carId, final UpdateCarDTO updateCarDTO);
}

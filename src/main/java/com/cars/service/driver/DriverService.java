package com.cars.service.driver;

import java.util.List;

import com.cars.datatransferobject.filter.car.CarCriteriaFilterDTO;
import com.cars.datatransferobject.filter.driver.DriverCriteriaFilterDTO;
import com.cars.domainobject.car.UseCarDO;
import com.cars.domainobject.driver.DriverDO;
import com.cars.domainvalue.OnlineStatus;
import com.cars.service.GlobalService;

/**
 * Interface for Driver Service typed to It's domain object -> DriverDO
 */
public interface DriverService extends GlobalService<DriverDO>
{

    void updateLocation(final long driverId, final double longitude, final double latitude);

    List<DriverDO> findByStatus(final OnlineStatus onlineStatus);

    List<UseCarDO> filter(final DriverCriteriaFilterDTO driverCriteriaFilterDTO, final CarCriteriaFilterDTO carFilter);
}

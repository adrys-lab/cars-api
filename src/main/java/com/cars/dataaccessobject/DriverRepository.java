package com.cars.dataaccessobject;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cars.domainobject.driver.DriverDO;
import com.cars.domainvalue.OnlineStatus;

@Repository
public interface DriverRepository extends GlobalRepository<DriverDO> {
    List<DriverDO> findByOnlineStatus(final OnlineStatus onlineStatus);
}

package com.cars.dataaccessobject;

import org.springframework.stereotype.Repository;

import com.cars.domainobject.car.CarDO;

@Repository
public interface CarRepository extends GlobalRepository<CarDO> {
}

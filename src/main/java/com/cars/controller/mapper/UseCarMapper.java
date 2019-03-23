package com.cars.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.cars.datatransferobject.car.UseCarDTO;
import com.cars.domainobject.car.CarDO;
import com.cars.domainobject.car.UseCarDO;
import com.cars.domainobject.driver.DriverDO;

public class UseCarMapper
{
    public static List<UseCarDTO> makeUseCarDTO(final Iterable<UseCarDO> useCarDO)
    {
        return StreamSupport
                .stream(useCarDO.spliterator(), false)
                .map(UseCarMapper::makeUseCarDTO)
                .collect(Collectors.toList());
    }

    public static UseCarDTO makeUseCarDTO(final UseCarDO useCarDO)
    {
        final CarDO car = useCarDO.getCar();
        final DriverDO driver = useCarDO.getDriver();
        if (Objects.nonNull(car) && Objects.nonNull(driver))
        {
            return UseCarDTO.newBuilder()
                    .setCar(CarMapper.makeCarDTO(useCarDO.getCar()))
                    .setDriver(DriverMapper.makeDriverDTO(useCarDO.getDriver()))
                    .build();
        }
        return new UseCarDTO();
    }

    public static UseCarDTO makeUseCarDTOOnlyDriver(final DriverDO driverDO)
    {
        return UseCarDTO.newBuilder()
                .setDriver(DriverMapper.makeDriverDTO(driverDO))
                .build();
    }

    public static UseCarDO makeUseCarDO(final DriverDO driverDO, final CarDO car)
    {
        if (Objects.nonNull(driverDO))
        {
            return new UseCarDO (car, driverDO);
        }
        return new UseCarDO();
    }

    public static List<UseCarDTO> makeUseCarDTOList(Collection<UseCarDO> usedCars)
    {
        return usedCars.stream()
            .map(UseCarMapper::makeUseCarDTO)
            .collect(Collectors.toList());
    }
}

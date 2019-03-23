package com.cars.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.cars.datatransferobject.driver.DriverDTO;
import com.cars.domainobject.driver.DriverDO;
import com.cars.domainvalue.GeoCoordinate;

public class DriverMapper
{
    public static DriverDO makeDriverDO(DriverDTO driverDTO)
    {
        return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword());
    }


    public static List<DriverDTO> makeDriverDTO(final Iterable<DriverDO> driverDO)
    {
        return StreamSupport
                .stream(driverDO.spliterator(), false)
                .map(DriverMapper::makeDriverDTO)
                .collect(Collectors.toList());
    }

    public static List<DriverDTO> makeDriverDTO(final List<DriverDO> driverDO)
    {
        return driverDO.stream().map(DriverMapper::makeDriverDTO).collect(Collectors.toList());
    }

    public static DriverDTO makeDriverDTO(DriverDO driverDO)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(driverDO.getId())
            .setPassword(driverDO.getPassword())
            .setUsername(driverDO.getUsername());

        final GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            driverDTOBuilder.setCoordinate(coordinate);
        }

        return driverDTOBuilder.createDriverDTO();
    }


    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers)
    {
        return drivers.stream()
            .map(DriverMapper::makeDriverDTO)
            .collect(Collectors.toList());
    }
}

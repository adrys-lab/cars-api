package com.cars.controller.mapper;

import com.cars.datatransferobject.car.ManufacturerDTO;
import com.cars.domainobject.car.ManufacturerDO;

class ManufacturerMapper
{

    static ManufacturerDTO makeManufacturerDTO(final ManufacturerDO manufacturerDO)
    {
        if (manufacturerDO != null)
        {
            return ManufacturerDTO.newBuilder()
                    .setId(manufacturerDO.getId())
                    .setName(manufacturerDO.getName())
                    .setDescription(manufacturerDO.getDescription())
                    .setCountry(manufacturerDO.getCountry())
                    .build();
        }
        return new ManufacturerDTO();
    }

    static ManufacturerDO makeManufacturerDO(final ManufacturerDTO manufacturerDO)
    {
        if (manufacturerDO != null)
        {
            return new ManufacturerDO(manufacturerDO.getId(),
                    manufacturerDO.getName(),
                    manufacturerDO.getDescription(),
                    manufacturerDO.getCountry());
        }
        return new ManufacturerDO();
    }

}

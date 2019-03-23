package com.cars.controller.mapper;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.cars.datatransferobject.car.CarDTO;
import com.cars.datatransferobject.car.UpdateCarDTO;
import com.cars.domainobject.car.CarDO;
import com.cars.domainobject.car.ManufacturerDO;
import com.cars.model.car.EngineType;

public class CarMapper
{

    public static CarDO makeCarDOOnlyID(final Long carId)
    {
        final CarDO carDO = new CarDO();
        carDO.setId(carId);
        return carDO;
    }
    public static CarDTO makeCarDTO(final CarDO carDO)
    {
        return CarDTO.newBuilder()
                .setId(carDO.getId())
                .setLicensePlate(carDO.getLicensePlate())
                .setSeatCount(carDO.getSeatCount())
                .setConvertible(carDO.getConvertible())
                .setRating(carDO.getRating())
                .setEngineType(carDO.getEngineType())
                .setManufacturer(ManufacturerMapper.makeManufacturerDTO(carDO.getManufacturer()))
            .build();
    }

    public static CarDO makeCarDO(final CarDTO carDTO)
    {
        return new CarDO(carDTO.getLicensePlate(),
                carDTO.getSeatCount(),
                carDTO.getConvertible(),
                carDTO.getRating(),
                carDTO.getEngineType(),
                ManufacturerMapper.makeManufacturerDO(carDTO.getManufacturer()));
    }

    /*
    * Compare the existing and new CarDO.
    * Update only existing properties present in the new one.
    */
    public static CarDO merge(final CarDO existingCarDO, final UpdateCarDTO update)
    {
        final String licensePlate = StringUtils.isBlank(update.getLicensePlate()) ? existingCarDO.getLicensePlate() : update.getLicensePlate();
        final Integer seatCount = Objects.isNull(update.getSeatCount()) ? existingCarDO.getSeatCount() : update.getSeatCount();
        final Boolean convertible = Objects.isNull(update.getConvertible()) ? existingCarDO.getConvertible() : update.getConvertible();
        final Double rating = Objects.isNull(update.getRating()) ? existingCarDO.getRating() : update.getRating();
        final EngineType engineType = Objects.isNull(update.getEngineType()) ? existingCarDO.getEngineType() : update.getEngineType();
        final ManufacturerDO manufacturer = mergeManufacturer(existingCarDO, update);
        final CarDO carDO = new CarDO(licensePlate,seatCount,convertible,rating,engineType, manufacturer);
        carDO.setId(existingCarDO.getId());
        return carDO;
    }

    private static ManufacturerDO mergeManufacturer(CarDO existingCarDO, UpdateCarDTO update)
    {
        return Objects.isNull(update.getManufacturer()) ? existingCarDO.getManufacturer() :
                Objects.isNull(update.getManufacturer().getId()) ? existingCarDO.getManufacturer() : ManufacturerMapper.makeManufacturerDO(update.getManufacturer());
    }
}

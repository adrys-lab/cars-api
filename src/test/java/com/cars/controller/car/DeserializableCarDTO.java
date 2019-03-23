package com.cars.controller.car;

import java.io.Serializable;

import com.cars.datatransferobject.car.ManufacturerDTO;
import com.cars.model.car.EngineType;


/*
 * Include ID for deserialization.
 * We want all fields deserialized.
 */
public class DeserializableCarDTO implements Serializable {

    private static final long serialVersionUID = -926512398131254778L;

    private Long id;

    private String licensePlate;

    private Integer seatCount;

    private Boolean convertible;

    private Double rating;

    private EngineType engineType;

    private ManufacturerDTO manufacturer;

    private DeserializableCarDTO() {
    }

    private DeserializableCarDTO(final Long id, final String licensePlate, final Integer seatCount, final Boolean convertible,
            final Double rating, final EngineType engineType, final ManufacturerDTO manufacturer) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
    }

    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public Boolean getConvertible() {
        return convertible;
    }

    public Double getRating() {
        return rating;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public ManufacturerDTO getManufacturer() {
        return manufacturer;
    }
}
package com.cars.controller.driver;

import java.io.Serializable;

import com.cars.controller.car.DeserializableCarDTO;

/*
 * Include ID for deserialization.
 * We want all fields deserialized.
 */
public class DeserializableUseCarDTO implements Serializable {

    DeserializableCarDTO carDTO;

    DeserializableDriverDTO driverDTO;

    public DeserializableUseCarDTO() {

    }
    public DeserializableUseCarDTO(DeserializableCarDTO carDTO, DeserializableDriverDTO driverDTO) {
        this.carDTO = carDTO;
        this.driverDTO = driverDTO;
    }

    public DeserializableCarDTO getCarDTO() {
        return carDTO;
    }

    public DeserializableDriverDTO getDriverDTO() {
        return driverDTO;
    }
}
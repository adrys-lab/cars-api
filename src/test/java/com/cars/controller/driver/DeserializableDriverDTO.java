package com.cars.controller.driver;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cars.controller.car.DeserializableCarDTO;
import com.cars.domainvalue.GeoCoordinate;

/*
 * Include ID for deserialization.
 * We want all fields deserialized.
 */
public class DeserializableDriverDTO implements Serializable {

    private Long id;

    private String username;

    private String password;

    @JsonIgnore
    private GeoCoordinate coordinate;

    private DeserializableCarDTO carDTO;


    private DeserializableDriverDTO()
    {
    }


    private DeserializableDriverDTO(final Long id,
            final String username,
            final String password,
            final GeoCoordinate coordinate,
            final DeserializableCarDTO carDTO)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coordinate = coordinate;
        this.carDTO = carDTO;
    }


    public Long getId()
    {
        return id;
    }


    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }

    public DeserializableCarDTO getCarDTO() {
        return carDTO;
    }

}
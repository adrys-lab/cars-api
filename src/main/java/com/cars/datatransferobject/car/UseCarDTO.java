package com.cars.datatransferobject.car;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.cars.datatransferobject.driver.DriverDTO;

/*
* DTO for Use Car.
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UseCarDTO implements Serializable
{

    private static final long serialVersionUID = -926512398131254778L;


    CarDTO carDTO;

    DriverDTO driverDTO;

    public UseCarDTO()
    {
    }

    public UseCarDTO(CarDTO carDTO, DriverDTO driverDTO)
    {
        this.carDTO = carDTO;
        this.driverDTO = driverDTO;
    }

    public CarDTO getCarDTO()
    {
        return carDTO;
    }

    public DriverDTO getDriverDTO()
    {
        return driverDTO;
    }

    public static UseCarDTOBuilder newBuilder()
    {
        return new UseCarDTOBuilder();
    }

    public static class UseCarDTOBuilder
    {

        private CarDTO car;
        private DriverDTO driver;

        public UseCarDTOBuilder setCar(CarDTO car)
        {
            this.car = car;
            return this;
        }

        public UseCarDTOBuilder setDriver(DriverDTO driver)
        {
            this.driver = driver;
            return this;
        }

        public UseCarDTO build()
        {
            return new UseCarDTO(car, driver);
        }

    }
}

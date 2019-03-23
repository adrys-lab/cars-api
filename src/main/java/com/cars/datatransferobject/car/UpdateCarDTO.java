package com.cars.datatransferobject.car;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.cars.domainobject.car.CarDO;
import com.cars.model.car.EngineType;
import com.cars.platform.security.annotation.LicensePlateValidation;

/*
* DTO for Car Update with only Some fields -> non-mandatory.
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCarDTO implements Serializable
{

    private static final long serialVersionUID = -926512398131254778L;

    /*
     * Customized javax validation annotation
     */
    @LicensePlateValidation(nullable = true)
    private String licensePlate;

    @Min(value=3, message = "Minimum Car Number of Seats is 3.")
    @Max(value = 9, message = "Maximum Car Number of Seats is 9.")
    private Integer seatCount;

    private Boolean convertible;

    @Min(value = 0, message = "Minimum Car Rating value is 0.")
    @Max(value = 10, message = "Maximum Car Rating value is 10.")
    private Double rating;

    private EngineType engineType;

    private ManufacturerDTO manufacturer;

    private UpdateCarDTO()
    {
    }

    private UpdateCarDTO(final String licensePlate, final Integer seatCount, final Boolean convertible,
        final Double rating, final EngineType engineType, final ManufacturerDTO manufacturer)
    {
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
    }

    public String getLicensePlate()
    {
        return licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public Double getRating()
    {
        return rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public ManufacturerDTO getManufacturer()
    {
        return manufacturer;
    }

    public static CarDTOBuilder newBuilder()
    {
        return new CarDTOBuilder();
    }

    public static class CarDTOBuilder
    {
        private Long id;
        private String licensePlate;
        private Integer seatCount;
        private Boolean convertible;
        private Double rating;
        private EngineType engineType;
        private CarDO car;

        private ManufacturerDTO manufacturer;


        public CarDTOBuilder setId(final Long id)
        {
            this.id = id;
            return this;
        }


        public CarDTOBuilder setLicensePlate(final String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }


        public CarDTOBuilder setSeatCount(final Integer seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }


        public CarDTOBuilder setConvertible(final Boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }


        public CarDTOBuilder setRating(final Double rating)
        {
            this.rating = rating;
            return this;
        }


        public CarDTOBuilder setEngineType(final EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }

        public CarDTOBuilder setManufacturer(final ManufacturerDTO manufacturer)
        {
            this.manufacturer = manufacturer;
            return this;
        }

        public UpdateCarDTO build()
        {
            return new UpdateCarDTO(licensePlate, seatCount, convertible, rating, engineType, manufacturer);
        }

    }
}

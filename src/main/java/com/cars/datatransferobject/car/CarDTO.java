package com.cars.datatransferobject.car;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.cars.model.car.EngineType;
import com.cars.platform.security.annotation.LicensePlateValidation;

/*
* DTO for Car.
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO implements Serializable
{

    private static final long serialVersionUID = -926512398131254778L;

    /*
    * Read Only ID -> we don't want update it nor look for it (for searches, we use REST Search Approach by @PathVariable)
    */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    /*
     * Customized javax validation annotation
     */
    @LicensePlateValidation
    private String licensePlate;

    /*
    * seatCount has to be >=3 and <= 9, and not null for creation/update.
    */
    @Min(value=3, message = "Minimum Car Number of Seats is 3.")
    @Max(value = 9, message = "Maximum Car Number of Seats is 9.")
    @NotNull(message = "Car Seat Count is a mandatory field.")
    private Integer seatCount;

    @NotNull(message = "Car Convertible is a mandatory field.")
    private Boolean convertible;

    /*
    * rating has to be >= 0 and <= 10.
    */
    @Min(value = 0, message = "Minimum Car Rating value is 0.")
    @Max(value = 10, message = "Maximum Car Rating value is 10.")
    private Double rating;

    @NotNull(message = "Car Engine Type  is a mandatory field.")
    private EngineType engineType;

    /*
    * For create/update is needed an existing Manufacturer ID.
    * We don't want Cars without manufacturer in DDBB.
    */
    @NotNull(message = "Car Manufacturer is a mandatory field.")
    private ManufacturerDTO manufacturer;

    private CarDTO()
    {
    }

    private CarDTO(final Long id, final String licensePlate, final Integer seatCount, final Boolean convertible,
        final Double rating, final EngineType engineType, final ManufacturerDTO manufacturer)
    {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
    }

    @JsonProperty
    public Long getId()
    {
        return id;
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

        public CarDTO build()
        {
            return new CarDTO(id, licensePlate, seatCount, convertible, rating, engineType, manufacturer);
        }

    }
}

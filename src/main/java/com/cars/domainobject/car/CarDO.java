package com.cars.domainobject.car;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.cars.domainobject.DomainObject;
import com.cars.model.car.EngineType;

/*
* DO for Cars.
* Set Unique constraint for License Plate.
*/
@Entity
@Table(
    name = "car",
    uniqueConstraints = @UniqueConstraint(name = "uc_car_licensePlate", columnNames = {"licensePlate"}))
public class CarDO implements DomainObject
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_seq")
    @SequenceGenerator(name = "car_seq", sequenceName = "car_seq")
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column(nullable = false, length=7, unique = true)
    @NotNull(message = "Car License Plate is a mandatory field.")
    private String licensePlate;

    @Column(nullable = false)
    @NotNull(message = "Car Seat Count is a mandatory field.")
    private Integer seatCount;

    @Column(columnDefinition = "boolean default false")
    @NotNull(message = "Car Convertible is a mandatory field.")
    private Boolean convertible;

    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    private Double rating;

    @Column(name = "engine_type")
    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    /*
    * When a car update is requested, manufacturer can only be updated through foreign key.
    * Manufacturer data can not be changed --> that's why we don't define any Cascade attribute in the manyToOne annotation.
    */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="manufacturer")
    @NotNull(message = "Car Manufacturer is a mandatory field.")
    private ManufacturerDO manufacturer;

    public CarDO()
    {
    }

    public CarDO(final String licensePlate, final Integer seatCount, final Boolean convertible,
        final Double rating, final EngineType engineType, final ManufacturerDO manufacturer)
    {
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
    }


    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public void setId(final Long id)
    {
        this.id = id;
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


    public ManufacturerDO getManufacturer()
    {
        return manufacturer;
    }

    public ZonedDateTime getDateCreated()
    {
        return dateCreated;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    @Override
    public void setDeleted(final boolean deleted)
    {
        this.deleted = deleted;
    }
}

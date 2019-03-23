package com.cars.domainobject.car;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.cars.domainobject.DomainObject;

/*
* DO for Manufacturers
* Is preferred to create a new table because it contains more information than just the name/ID.
* Set Constraint for Manufacturer name -> we don't want 2 manufacturers with same name.
*/
@Entity
@Table(
    name = "manufacturer",
    uniqueConstraints = @UniqueConstraint(name = "uc_manufacturer_name", columnNames = {"name"}))
public class ManufacturerDO implements DomainObject
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "man_seq")
    @SequenceGenerator(name = "man_seq", sequenceName = "man_seq")
    private Long id;

    @Column(name = "date_created", length = 50)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(name = "name", length = 50, unique = true)
    @NotNull(message = "Manufacturer Name is a mandatory field.")
    private String name;

    @Column(name = "description", length = 50)
    @NotNull(message = "Manufacturer Description is a mandatory field.")
    private String description;

    @Column(name="country", length = 50)
    private String country;

    public ManufacturerDO()
    {
    }

    public ManufacturerDO(final Long id, final String name, final String description, final String country)
    {
        this.id = id;
        this.dateCreated = ZonedDateTime.now();
        this.name = name;
        this.description = description;
        this.country = country;
    }

    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public void setId(Long id)
    {
        //DO NOTHING
    }

    @Override
    public void setDeleted(boolean deleted)
    {
        //DO NOTHING
    }

    public ZonedDateTime getDateCreated()
    {
        return dateCreated;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getCountry()
    {
        return country;
    }
}

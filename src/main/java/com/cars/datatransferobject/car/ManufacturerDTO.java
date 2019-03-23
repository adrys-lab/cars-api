package com.cars.datatransferobject.car;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
* DTO for Manufacturer
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManufacturerDTO implements Serializable
{

    private static final long serialVersionUID = -926512398731238978L;

    @NotNull(message = "Manufacturer Name is a mandatory field.")
    private Long id;

    /*
     * Set READ_ONLY for name, description and country.
     * When update a car, we don't want to allow modifying manufacturer properties,
     * we only want to update the entity relation --> allow change the Car manufacturer from 1 to 2.
     */
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String description;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String country;

    public ManufacturerDTO()
    {
    }

    public ManufacturerDTO(final Long id, final String name, final String description, final String country)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.country = country;
    }

    public Long getId()
    {
        return id;
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

    public static ManufacturerDTOBuilder newBuilder()
    {
        return new ManufacturerDTOBuilder();
    }

    public static class ManufacturerDTOBuilder
    {
        private Long id;
        private String name;
        private String description;
        private String country;

        public ManufacturerDTOBuilder setId(final Long id)
        {
            this.id = id;
            return this;
        }

        public ManufacturerDTOBuilder setName(final String name)
        {
            this.name = name;
            return this;
        }

        public ManufacturerDTOBuilder setDescription(final String description)
        {
            this.description = description;
            return this;
        }

        public ManufacturerDTOBuilder setCountry(final String country)
        {
            this.country = country;
            return this;
        }

        public ManufacturerDTO build()
        {
            return new ManufacturerDTO(id, name, description, country);
        }

    }
}

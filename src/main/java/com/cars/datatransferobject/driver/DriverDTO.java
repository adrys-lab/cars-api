package com.cars.datatransferobject.driver;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.cars.domainvalue.GeoCoordinate;

/*
* DTO for Driver
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDTO implements Serializable
{
    private static final long serialVersionUID = -926512313240982378L;

    @JsonIgnore
    private Long id;

    @NotNull(message = "Username can not be null!")
    private String username;

    @NotNull(message = "Password can not be null!")
    private String password;

    private GeoCoordinate coordinate;

    private DriverDTO()
    {
    }


    private DriverDTO(final Long id,
            final String username,
            final String password,
            final GeoCoordinate coordinate)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coordinate = coordinate;
    }


    @JsonProperty
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

    public static DriverDTOBuilder newBuilder()
    {
        return new DriverDTOBuilder();
    }

    public static class DriverDTOBuilder
    {
        private Long id;
        private String username;
        private String password;
        private GeoCoordinate coordinate;


        public DriverDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public DriverDTOBuilder setUsername(String username)
        {
            this.username = username;
            return this;
        }


        public DriverDTOBuilder setPassword(String password)
        {
            this.password = password;
            return this;
        }


        public DriverDTOBuilder setCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }

        public DriverDTO createDriverDTO()
        {
            return new DriverDTO(id, username, password, coordinate);
        }

    }

    /*
     * We need implement our custom equals as Comparator will call it for DriverController.class calls Collections.removeAll() method.
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null || !(o instanceof DriverDTO))
        {
            return false;
        } else {
            final DriverDTO compared = (DriverDTO) o;
            return this.getId().equals(compared.getId()) &&
                    this.getUsername().equals(compared.getUsername()) &&
                    this.getPassword().equals(compared.getPassword());
        }
    }
}

package com.cars.domainobject.car;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cars.domainobject.DomainObject;
import com.cars.domainobject.driver.DriverDO;

/*
* DO for Cars.
* Set Unique constraint for Car -> Car can only be driven by one driver.
* Set Unique constraint for Driver -> Driver can only drive one car at same time.
* Even it's controlled by code, set constraints too.
*/
@Entity
@Table(
    name = "use_car",
    uniqueConstraints = {
            @UniqueConstraint(name = "uc_car", columnNames = {"car"}),
            @UniqueConstraint(name = "uc_driver", columnNames = {"driver"})
    })
public class UseCarDO implements DomainObject
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "use_car_seq")
    @SequenceGenerator(name = "use_car_seq", sequenceName = "use_car_seq")
    private Long id;

    /*
     * Set a unique constraint on driver -> Driver can only be drive one car.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="driver", unique = true, referencedColumnName = "id")
    private DriverDO driver;

    /*
     * Set a unique constraint on car -> Car can only be driven by one driver.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="car", unique = true, referencedColumnName = "id")
    private CarDO car;

    public UseCarDO()
    {
    }

    public UseCarDO(final CarDO car, final DriverDO driver)
    {
        this.driver = driver;
        this.car = car;
    }

    public CarDO getCar()
    {
        return car;
    }

    public DriverDO getDriver()
    {
        return driver;
    }

    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public void setId(Long id)
    {
    }

    @Override
    public void setDeleted(boolean deleted)
    {
    }
}

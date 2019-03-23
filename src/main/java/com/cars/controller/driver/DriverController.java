package com.cars.controller.driver;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cars.controller.ApiVersionController;
import com.cars.controller.mapper.DriverMapper;
import com.cars.controller.mapper.UseCarMapper;
import com.cars.datatransferobject.car.UseCarDTO;
import com.cars.datatransferobject.driver.DriverDTO;
import com.cars.datatransferobject.filter.car.CarCriteriaFilterDTO;
import com.cars.datatransferobject.filter.driver.DriverCriteriaFilterDTO;
import com.cars.datatransferobject.filter.field.Condition;
import com.cars.datatransferobject.filter.field.FilterField;
import com.cars.datatransferobject.filter.field.StringFilterField;
import com.cars.domainobject.car.CarDO;
import com.cars.domainobject.car.UseCarDO;
import com.cars.domainobject.driver.DriverDO;
import com.cars.domainvalue.OnlineStatus;
import com.cars.model.exception.external.car.CarAlreadyInUseException;
import com.cars.model.exception.internal.driver.DriverAlreadyDrivingException;
import com.cars.model.exception.internal.driver.DriverIsNotDrivingException;
import com.cars.model.exception.internal.driver.DriverOfflineException;
import com.cars.service.car.CarService;
import com.cars.service.driver.DriverService;
import com.cars.service.usecar.UseCarService;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/drivers")
public class DriverController extends ApiVersionController
{

    @Autowired
    private final DriverService driverService;
    @Autowired
    private final CarService carService;
    @Autowired
    private final UseCarService useCarService;


    public DriverController(final DriverService driverService, final CarService carService, final UseCarService useCarService)
    {
        this.driverService = driverService;
        this.carService = carService;
        this.useCarService = useCarService;
    }


    @Description(value = "Method to get a new driver.")
    @GetMapping("/{driverId}")
    @PreAuthorize("hasRole('READER')")
    public DriverDTO getDriver(
            @RequestHeader(name="Authorization") final String token,
            @Valid @PathVariable final long driverId)
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }

    @Description(value = "Method to create a new driver.")
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('WRITER')")
    public DriverDTO createDriver(
            @RequestHeader(name="Authorization") final String token,
            @Valid @RequestBody final DriverDTO driverDTO)
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }

    @Description(value = "Method to update an existing driver.")
    @PostMapping("/update/{driverId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('WRITER')")
    public DriverDTO updateDriver(
            @RequestHeader(name="Authorization") final String token,
            @PathVariable long driverId,
            @Valid @RequestBody final DriverDTO driverDTO)
    {
        final DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.update(driverId, driverDO));
    }


    @Description(value = "Method to logicalDelete an existing driver.")
    @DeleteMapping("/{driverId}")
    @PreAuthorize("hasRole('WRITER')")
    public void deleteDriver(
            @RequestHeader(name="Authorization") final String token,
            @Valid @PathVariable final long driverId)
    {
        driverService.delete(driverService.find(driverId));
    }


    @Description(value = "Method to update location an existing driver.")
    @PostMapping("/update/location/{driverId}")
    @PreAuthorize("hasRole('WRITER')")
    public void updateLocation(
            @RequestHeader(name="Authorization") final String token,
            @Valid @PathVariable long driverId,
            @RequestParam double longitude,
            @RequestParam double latitude)
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @Description(value = "Method get all existing drivers.")
    @GetMapping
    @PreAuthorize("hasRole('READER')")
    public List<DriverDTO> findDrivers(
            @RequestHeader(name="Authorization") final String token,
            @RequestParam OnlineStatus onlineStatus)
    {
        return DriverMapper.makeDriverDTOList(driverService.findByStatus(onlineStatus));
    }


    /*
    * Method where a driver choose a car to drive.
    * Restrictions:
    *  - Driver has to be online
    *  - only 1 car be driven at same time (controlled in code & constraint).
    *  - Driver car drive only 1 car at same time (controlled in code & constraint).
    */
    @Description(value = "Method to make a driver drive a car.")
    @PostMapping("/{driverId}/use/{carId}")
    @PreAuthorize("hasRole('WRITER')")
    public ResponseEntity<UseCarDTO> use(
            @RequestHeader(name="Authorization") final String token,
            @Valid @PathVariable long driverId,
            @Valid @PathVariable long carId) throws CarAlreadyInUseException
    {
        final DriverDO driverDO = driverService.find(driverId);
        if (driverDO.getOnlineStatus().equals(OnlineStatus.OFFLINE))
        {
            throw new DriverOfflineException();
        } else {
            final CarDO carDO = carService.find(carId);
            if (useCarService.findByCar(carDO) != null)
            {
                throw new CarAlreadyInUseException();
            }
            if (useCarService.findByDriver(driverDO) != null)
            {
                throw new DriverAlreadyDrivingException();
            }
            final UseCarDO useCarDO = useCarService.create(UseCarMapper.makeUseCarDO(driverDO, carDO));
            return ResponseEntity.ok(UseCarMapper.makeUseCarDTO(useCarDO));
        }
    }

    /*
    * No need to send the carId, since a driver can only drive 1 car.
    * So we only need to update this driver with car field empty.
    */
    @Description(value = "Method to make a driver leave a car.")
    @PostMapping("/{driverId}/leaveCar")
    @PreAuthorize("hasRole('WRITER')")
    public ResponseEntity<UseCarDTO> leaveCar(
            @RequestHeader(name="Authorization") final String token,
            @Valid @PathVariable long driverId)
    {
        final UseCarDO useCarDO = useCarService.findByDriver(driverService.find(driverId));
        if(Objects.isNull(useCarDO))
        {
            throw new DriverIsNotDrivingException();
        }
        useCarService.delete(useCarDO);
        return ResponseEntity.ok(UseCarMapper.makeUseCarDTOOnlyDriver((useCarDO.getDriver())));
    }

    /*
    * find all drivers who are driving
    */
    @Description(value = "Method to get all drivers who are driving some car.")
    @GetMapping("/driving")
    @PreAuthorize("hasRole('READER')")
    public ResponseEntity<List<UseCarDTO>> driving(
            @RequestHeader(name="Authorization") final String token)
    {
        return ResponseEntity.ok(UseCarMapper.makeUseCarDTO(useCarService.findAll()));
    }

    /*
    * find all drivers who are driving
    */
    @Description(value = "Method to get all drivers who are driving NOT driving -> who are free.")
    @GetMapping("/freeDrivers")
    @PreAuthorize("hasRole('READER')")
    public ResponseEntity<List<DriverDTO>> freeDrivers(
            @RequestHeader(name="Authorization") final String token)
    {
        final List<UseCarDTO> driving = UseCarMapper.makeUseCarDTO(useCarService.findAll());
        final List<DriverDTO> allDrivers = DriverMapper.makeDriverDTO(driverService.findAll());
        allDrivers.removeAll(driving.stream().map(UseCarDTO::getDriverDTO).collect(Collectors.toList()));
        return ResponseEntity.ok(allDrivers);
    }

    /*
    * Method for Driver filter depending on a Filter.
    * We use POST Method cause we are posting a search action.
    * In addition, is prefered to use the POST Body for Querying (in REST terms of posting a search).
    * GET Method doesn't have/allow Body Part -> So no RequestBody can be used.
    */
    @Description(value = "Method to filter drivers with cars with given properties.")
    @PostMapping("/filter")
    @PreAuthorize("hasRole('WRITER')")
    public ResponseEntity<List<UseCarDTO>> filter(
            @RequestHeader(name="Authorization") final String token,
            @Valid @RequestBody final CarCriteriaFilterDTO carCriteriaFilterDTO)
    {
        /*
        * Start creating a Filter for Drivers -> I assumed to only retrieve drivers online and not deleted
        */
        final StringFilterField status = new StringFilterField(Condition.EQUALS, OnlineStatus.ONLINE.name());
        final FilterField<Boolean> deleted = new FilterField<>(Condition.EQUALS, false);

        final DriverCriteriaFilterDTO driverCriteriaFilterDTO = new DriverCriteriaFilterDTO(status, deleted);
        return ResponseEntity.ok(UseCarMapper.makeUseCarDTO(driverService.filter(driverCriteriaFilterDTO, carCriteriaFilterDTO)));
    }
}

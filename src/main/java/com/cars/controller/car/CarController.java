package com.cars.controller.car;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cars.controller.ApiVersionController;
import com.cars.controller.mapper.CarMapper;
import com.cars.datatransferobject.car.CarDTO;
import com.cars.datatransferobject.car.UpdateCarDTO;
import com.cars.domainobject.car.CarDO;
import com.cars.model.exception.internal.car.CarNotFoundException;
import com.cars.service.car.CarService;

/**
 * All operations with a car will be routed by this controller.
 * Understanding methods as REST defines:
 * PUT -> new resource added
 * POST -> modify existing resource
 * GET -> get a resource
 * DELETE -> remove a resource
 * <p/>
 */
@RestController
@RequestMapping("/cars")
public class CarController extends ApiVersionController
{

    @Autowired
    private CarService carService;

    @Description(value = "Method to get a car.")
    @GetMapping("/{carId}")
    @PreAuthorize("hasRole('READER')")
    public CarDTO getCar(
            @RequestHeader(name="Authorization") final String token,
            @Valid @PathVariable long carId) throws CarNotFoundException
    {
        return CarMapper.makeCarDTO(carService.find(carId));
    }


    @Description(value = "Method to get all existing cars.")
    @GetMapping
    @PreAuthorize("hasRole('READER')")
    public List<CarDTO> getCars(
            @RequestHeader(name="Authorization") final String token)
    {
        return StreamSupport
                .stream(carService.findAll().spliterator(), false)
                .map(CarMapper::makeCarDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a car.
     * Manufacturer ID shouldn't be null and should exist -> not allowed to create new manufacturers in this endpoint
     */
    @Description(value = "Method to create a car.")
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('WRITER')")
    public CarDTO createCar(
            @RequestHeader(name="Authorization") final String token,
            @Valid @RequestBody final CarDTO carDTO)
    {
        final CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }

    /**
     * Update an existing car.
     * Used to update the entire car properties.
     * Manufacturer ID shouldn't be null and should exist -> not allowed to create new manufacturers in this endpoint
     */
    @Description(value = "Method to update an existing car.")
    @PostMapping("/{carId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('WRITER')")
    public CarDTO updateCar(
            @RequestHeader(name="Authorization") final String token,
            @Valid @PathVariable long carId,
            @Valid @RequestBody final CarDTO carDTO)
    {
        final CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.update(carId, carDO));
    }

    /**
     * Update an existing car.
     * Used to update only the informed properties -> done to not have to send whole json DTO for car, only properties wanted to updated.
     * Manufacturer ID shouldn't be null and should exist -> not allowed to create new manufacturers in this endpoint
     */
    @Description(value = "Method to update an existing car.")
    @PostMapping("/some/{carId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('WRITER')")
    public CarDTO updateCar(
            @RequestHeader(name="Authorization") final String token,
            @Valid @PathVariable long carId,
            @Valid @RequestBody final UpdateCarDTO updateCarDTO)
    {
        return CarMapper.makeCarDTO(carService.updateSome(carId, updateCarDTO));
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @Description(value = "Method to logicalDelete an existing car.")
    @DeleteMapping("/{carId}")
    @PreAuthorize("hasRole('WRITER')")
    public CarDTO deleteCar(
            @RequestHeader(name="Authorization") final String token,
            @Valid @PathVariable final  long carId) throws CarNotFoundException
    {
        return CarMapper.makeCarDTO(carService.delete(carService.find(carId)));
    }
}

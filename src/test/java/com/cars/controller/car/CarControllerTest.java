package com.cars.controller.car;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.cars.CommonPlatformTest;
import com.cars.datatransferobject.car.CarDTO;

/*
 * Integration Test for Car Controller.
 * Annotate needed methods with Transactional -> we don't want the changes keep in DDBB after test finishes (because of test change data).
 * This transactional annotation will mark the current thread as transactional-active --> Util for our transactionalUpdate and transactionalSave methods
 * in Abstract Global Service.
 */
@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CarControllerTest extends CommonPlatformTest
{

    private final String carOneId = "1";
    private final String carTwoId = "2";
    private static final String CAR_1_JSON = "/car_1.json";
    File car1;
    private CarDTO car1DTO;
    private static final String CAR_1_JSON_MODIFIED = "/car_1_modified.json";
    private static final String CAR_1_JSON_WITH_ID = "/car_1_with_id.json";
    private CarDTO car1DTOModified;

    @Before
    public void config() throws Exception
    {
        super.config();
        car1 = new File(this.getClass().getResource(CAR_1_JSON).getFile());
        car1DTO = modelifyJson(car1, CarDTO.class);
        final File car1Modified = new File(this.getClass().getResource(CAR_1_JSON_MODIFIED).getFile());
        car1DTOModified = modelifyJson(car1Modified, CarDTO.class);
    }

    @Test
    public void assertRolesForGetCar() throws Exception
    {
        getMvc().perform(MockMvcRequestBuilders.get(getCarMappedPath() + "/" + carOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.get(getCarMappedPath() + "/" + carOneId)
                .header(getTokenHeaderKey(), getReaderBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.get(getCarMappedPath() + "/" + carOneId)
                .header(getTokenHeaderKey(), getUnauthorizedBearer()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertRolesForGetCars() throws Exception
    {
        getMvc().perform(MockMvcRequestBuilders.get(getCarMappedPath())
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.get(getCarMappedPath())
                .header(getTokenHeaderKey(), getReaderBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.get(getCarMappedPath())
                .header(getTokenHeaderKey(), getUnauthorizedBearer()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertRolesForCreateCar() throws Exception
    {
        getMvc().perform(MockMvcRequestBuilders.put(getCarMappedPath())
                .header(getTokenHeaderKey(), getWriterBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(car1DTOModified)))
                .andExpect(status().isCreated());
        getMvc().perform(MockMvcRequestBuilders.put(getCarMappedPath())
                .header(getTokenHeaderKey(), getReaderBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(car1DTOModified)))
                .andExpect(status().isUnauthorized());
        getMvc().perform(MockMvcRequestBuilders.put(getCarMappedPath())
                .header(getTokenHeaderKey(), getUnauthorizedBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(car1DTOModified)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertRolesForUpdateCar() throws Exception
    {
        getMvc().perform(MockMvcRequestBuilders.post(getCarMappedPath() + "/4")
                .header(getTokenHeaderKey(), getWriterBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(car1DTOModified)))
                .andExpect(status().isCreated());
        getMvc().perform(MockMvcRequestBuilders.post(getCarMappedPath() + "/4")
                .header(getTokenHeaderKey(), getReaderBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(car1DTOModified)))
                .andExpect(status().isUnauthorized());
        getMvc().perform(MockMvcRequestBuilders.post(getCarMappedPath() + "/4")
                .header(getTokenHeaderKey(), getUnauthorizedBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(car1DTOModified)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertRolesForDeleteCar() throws Exception
    {
        getMvc().perform(MockMvcRequestBuilders.delete(getCarMappedPath() + "/4")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isAccepted());
        getMvc().perform(MockMvcRequestBuilders.delete(getCarMappedPath() + "/4")
                .header(getTokenHeaderKey(), getReaderBearer()))
                .andExpect(status().isUnauthorized());
        getMvc().perform(MockMvcRequestBuilders.delete(getCarMappedPath() + "/4")
                .header(getTokenHeaderKey(), getUnauthorizedBearer()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertCarOneIsCorrect() throws Exception
    {
        ResultActions resultActions =
                getMvc().perform(MockMvcRequestBuilders.get(getCarMappedPath() + "/" + carOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        final DeserializableCarDTO response = modelifyJson(resultActions.andReturn().getResponse().getContentAsString(), DeserializableCarDTO.class);
        AssertNothingEmpty(response);
        AssertAreEquals(response, modelifyJson(car1, DeserializableCarDTO.class));
    }

    @Test
    public void assertChangeAfterUpdate() throws Exception
    {
        ResultActions resultActions =
                getMvc().perform(MockMvcRequestBuilders.post(getCarMappedPath() + "/" + carTwoId)
                .header(getTokenHeaderKey(), getWriterBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(car1DTOModified)))
                .andExpect(status().isCreated());
        final DeserializableCarDTO response = modelifyJson(resultActions.andReturn().getResponse().getContentAsString(), DeserializableCarDTO.class);
        Assert.assertNotEquals(response.getLicensePlate(), car1DTO.getLicensePlate());
    }

    @Test
    public void assertChangeAfterCreate() throws Exception
    {
        MvcResult creationResult =
                getMvc().perform(MockMvcRequestBuilders.put(getCarMappedPath())
                        .header(getTokenHeaderKey(), getWriterBearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringifyJson(car1DTOModified)))
                .andExpect(status().isCreated())
                .andReturn();
        final DeserializableCarDTO createdCar = modelifyJson(creationResult.getResponse().getContentAsString(), DeserializableCarDTO.class);

        MvcResult getResult = getMvc().perform(MockMvcRequestBuilders.get(getCarMappedPath() + "/" + createdCar.getId())
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk())
                .andReturn();

        final DeserializableCarDTO getCar = modelifyJson(getResult.getResponse().getContentAsString(), DeserializableCarDTO.class);
        AssertAreEquals(createdCar, getCar);
    }

    @Test
    public void assertIdIsNotIntoAccountForUpdate() throws Exception
    {
        final File carWithId = new File(this.getClass().getResource(CAR_1_JSON_WITH_ID).getFile());
        final DeserializableCarDTO createCar = modelifyJson(carWithId, DeserializableCarDTO.class);
        MvcResult creationResult =
                getMvc().perform(MockMvcRequestBuilders.post(getCarMappedPath() + "/4")
                        .header(getTokenHeaderKey(), getWriterBearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringifyJson(createCar)))
                .andExpect(status().isCreated())
                .andReturn();
        final DeserializableCarDTO createdCar = modelifyJson(creationResult.getResponse().getContentAsString(), DeserializableCarDTO.class);

        Assert.assertNotEquals(createCar.getId(), createdCar.getId());
    }

    @Test
    public void assertIdIsNotIntoAccountForCreate() throws Exception
    {
        final File carWithId = new File(this.getClass().getResource(CAR_1_JSON_WITH_ID).getFile());
        DeserializableCarDTO createCar = modelifyJson(carWithId, DeserializableCarDTO.class);
        MvcResult creationResult =
                getMvc().perform(MockMvcRequestBuilders.put(getCarMappedPath())
                        .header(getTokenHeaderKey(), getWriterBearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringifyJson(createCar)))
                .andExpect(status().isCreated())
                .andReturn();
        final DeserializableCarDTO createdCar = modelifyJson(creationResult.getResponse().getContentAsString(), DeserializableCarDTO.class);

        Assert.assertNotEquals(createCar.getId(), createdCar.getId());
    }

    private void AssertNothingEmpty(final DeserializableCarDTO response)
    {
        Assert.assertTrue(StringUtils.isNotEmpty(response.getLicensePlate()));
        Assert.assertTrue(Objects.nonNull(response.getConvertible()));
        Assert.assertTrue(Objects.nonNull((response.getEngineType())));
        Assert.assertTrue(Objects.nonNull((response.getManufacturer().getId())));
        Assert.assertTrue(Objects.nonNull((response.getRating())));
        Assert.assertTrue(Objects.nonNull((response.getSeatCount())));
    }

    private void AssertAreEquals(final DeserializableCarDTO response, final DeserializableCarDTO car1DTO)
    {
        Assert.assertEquals(response.getLicensePlate(), car1DTO.getLicensePlate());
        Assert.assertEquals(response.getConvertible(), car1DTO.getConvertible());
        Assert.assertEquals((response.getEngineType()), car1DTO.getEngineType());
        Assert.assertEquals((response.getManufacturer().getId()), car1DTO.getManufacturer().getId());
        Assert.assertEquals((response.getRating()), car1DTO.getRating());
        Assert.assertEquals((response.getSeatCount()), car1DTO.getSeatCount());
    }
}
package com.cars.controller.driver;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

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
import com.cars.controller.car.DeserializableCarDTO;
import com.cars.datatransferobject.driver.DriverDTO;
import com.cars.datatransferobject.filter.car.CarCriteriaFilterDTO;

/*
 * Integration Test for Driver Controller.
 * Annotate class as Transactional -> we don't want the changes keep in DDBB after test finishes (because of test change data).
 * This transactional annotation will mark the current thread as transactional-active --> Util for our transactionalUpdate and transactionalSave methods
 * in Abstract Global Service.
 */
@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DriverControllerTest extends CommonPlatformTest
{

    private final String driverOneId = "1";
    private final String onlineDriver = "5";
    private static final String DRIVER_1_JSON = "/driver_1.json";
    private static final String DRIVER_2_JSON = "/driver_2.json";
    private DriverDTO driver1DTO;
    private DriverDTO driver2DTO;
    private static final String CAR_CRITERIA_FILTER = "/driver_filter.json";
    private static final String DRIVER_1_JSON_MODIFIED = "/driver_1_modified.json";
    private DriverDTO driver1DTOModified;
    private String carOneId = "1";
    private CarCriteriaFilterDTO carCriteriaFilter;

    @Before
    public void config() throws Exception {
        super.config();
        final File driver1 = new File(this.getClass().getResource(DRIVER_1_JSON).getFile());
        driver1DTO = modelifyJson(driver1, DriverDTO.class);
        final File driver2 = new File(this.getClass().getResource(DRIVER_2_JSON).getFile());
        driver2DTO = modelifyJson(driver2, DriverDTO.class);
        final File driver1Modified = new File(this.getClass().getResource(DRIVER_1_JSON_MODIFIED).getFile());
        driver1DTOModified = modelifyJson(driver1Modified, DriverDTO.class);
        final File carCriteriaFilterFile = new File(this.getClass().getResource(CAR_CRITERIA_FILTER).getFile());
        carCriteriaFilter = modelifyJson(carCriteriaFilterFile, CarCriteriaFilterDTO.class);
    }

    @Test
    public void assertRolesForGetDriver() throws Exception {
        getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "/" + driverOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "/" + driverOneId)
                .header(getTokenHeaderKey(), getReaderBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "/" + driverOneId)
                .header(getTokenHeaderKey(), getUnauthorizedBearer()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertRolesForGetDrivers() throws Exception {
        getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "?onlineStatus=ONLINE")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "?onlineStatus=ONLINE")
                .header(getTokenHeaderKey(), getReaderBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "?onlineStatus=ONLINE")
                .header(getTokenHeaderKey(), getUnauthorizedBearer()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertRolesForCreateDriver() throws Exception {
        getMvc().perform(MockMvcRequestBuilders.put(getDriverMappedPath())
                .header(getTokenHeaderKey(), getWriterBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(driver1DTOModified)))
                .andExpect(status().isCreated());
        getMvc().perform(MockMvcRequestBuilders.put(getDriverMappedPath())
                .header(getTokenHeaderKey(), getReaderBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(driver1DTOModified)))
                .andExpect(status().isUnauthorized());
        getMvc().perform(MockMvcRequestBuilders.put(getDriverMappedPath())
                .header(getTokenHeaderKey(), getUnauthorizedBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(driver1DTOModified)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertRolesForUpdateDriver() throws Exception {
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/update/4")
                .header(getTokenHeaderKey(), getWriterBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(driver1DTOModified)))
                .andExpect(status().isCreated());
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/update/4")
                .header(getTokenHeaderKey(), getReaderBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(driver1DTOModified)))
                .andExpect(status().isUnauthorized());
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/update/4")
                .header(getTokenHeaderKey(), getUnauthorizedBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(driver1DTOModified)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertRolesForDeleteDriver() throws Exception {
        getMvc().perform(MockMvcRequestBuilders.delete(getDriverMappedPath() + "/4")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.delete(getDriverMappedPath() + "/4")
                .header(getTokenHeaderKey(), getReaderBearer()))
                .andExpect(status().isUnauthorized());
        getMvc().perform(MockMvcRequestBuilders.delete(getDriverMappedPath() + "/4")
                .header(getTokenHeaderKey(), getUnauthorizedBearer()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertRolesForUseDriver() throws Exception {
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/use/" + carOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/use/" + carOneId)
                .header(getTokenHeaderKey(), getReaderBearer()))
                .andExpect(status().isUnauthorized());
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/use/" + carOneId)
                .header(getTokenHeaderKey(), getUnauthorizedBearer()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertRolesForLeaveCarDriver() throws Exception {
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/use/" + carOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/leaveCar")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/use/" + carOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/leaveCar")
                .header(getTokenHeaderKey(), getReaderBearer()))
                .andExpect(status().isUnauthorized());
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/leaveCar")
                .header(getTokenHeaderKey(), getUnauthorizedBearer()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertRolesFordriving() throws Exception {
        getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "/driving")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "/driving")
                .header(getTokenHeaderKey(), getReaderBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "/driving")
                .header(getTokenHeaderKey(), getUnauthorizedBearer()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertRolesForfreeDrivers() throws Exception {
        getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "/freeDrivers")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "/freeDrivers")
                .header(getTokenHeaderKey(), getReaderBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "/freeDrivers")
                .header(getTokenHeaderKey(), getUnauthorizedBearer()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void assertDriverOneIsCorrect() throws Exception {
        String driverTwoId = "2";
        ResultActions resultActions =
                getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "/" + driverTwoId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        final DriverDTO response = modelifyJson(resultActions.andReturn().getResponse().getContentAsString(), DriverDTO.class);
        AssertNothingEmpty(response);
        AssertAreEquals(response, driver2DTO);
    }

    @Test
    public void assertChangeAfterUpdate() throws Exception {
        ResultActions resultActions =
                getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/update/" + driverOneId)
                .header(getTokenHeaderKey(), getWriterBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(driver1DTOModified)))
                .andExpect(status().isCreated());
        final DriverDTO response = modelifyJson(resultActions.andReturn().getResponse().getContentAsString(), DriverDTO.class);
        Assert.assertNotEquals(response.getUsername(), driver1DTO.getUsername());
    }

    @Test
    public void testUseCarAlreadyInUse() throws Exception {
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/use/" + carOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        String anotherOnlineDriver = "6";
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + anotherOnlineDriver + "/use/" + carOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void testDriverIsAlreadyDriving() throws Exception {
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/use/" + carOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/use/" + carOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void testUseCarDriverNotDriving() throws Exception {
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/leaveCar")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testOfflineDriverCanNotDrive() throws Exception {
        String offlineDriver = "2";
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + offlineDriver + "/use/" + carOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testfreeDriversAndDrivingAreCorrect() throws Exception {
        MvcResult onlineDrivers = getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "?onlineStatus=ONLINE")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk())
        .andReturn();
        final DeserializableDriverDTO[] allOnlineDriversDO = modelifyJson(onlineDrivers.getResponse().getContentAsString(), DeserializableDriverDTO[].class);

        MvcResult offlineDrivers = getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "?onlineStatus=OFFLINE")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk())
        .andReturn();
        final DeserializableDriverDTO[] allOfflineDriversDO = modelifyJson(offlineDrivers.getResponse().getContentAsString(), DeserializableDriverDTO[].class);

        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/use/" + carOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult freeDriversResult = getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "/freeDrivers")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk())
                .andReturn();
        final DeserializableDriverDTO[] freeDrivers = modelifyJson(freeDriversResult.getResponse().getContentAsString(), DeserializableDriverDTO[].class);

        Assert.assertTrue(freeDrivers.length == allOfflineDriversDO.length + allOnlineDriversDO.length - 1);
        Assert.assertFalse(Arrays.stream(freeDrivers).map(DeserializableDriverDTO::getId).collect(Collectors.toList()).contains(Long.valueOf(onlineDriver)));

        MvcResult driving = getMvc().perform(MockMvcRequestBuilders.get(getDriverMappedPath() + "/driving")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk())
                .andReturn();
        final DeserializableUseCarDTO[] drivingDrivers = modelifyJson(driving.getResponse().getContentAsString(), DeserializableUseCarDTO[].class);
        Assert.assertTrue(drivingDrivers.length == 1);
        Assert.assertTrue(Arrays.stream(drivingDrivers).map(DeserializableUseCarDTO::getCarDTO).map(DeserializableCarDTO::getId).collect(Collectors.toList()).contains(Long.valueOf(carOneId)));
    }

    @Test
    public void testFilterDrivers() throws Exception {

        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/" + onlineDriver + "/use/" + carOneId)
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/6/use/2")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());
        getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/4/use/3")
                .header(getTokenHeaderKey(), getWriterBearer()))
                .andExpect(status().isOk());

        MvcResult freeDrivers = getMvc().perform(MockMvcRequestBuilders.post(getDriverMappedPath() + "/filter")
                .header(getTokenHeaderKey(), getWriterBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(carCriteriaFilter)))
                .andReturn();
        final DeserializableUseCarDTO[] filteredDrivers = modelifyJson(freeDrivers.getResponse().getContentAsString(), DeserializableUseCarDTO[].class);

        Assert.assertTrue(Arrays.stream(filteredDrivers).map(DeserializableUseCarDTO::getDriverDTO).map(DeserializableDriverDTO::getId).collect(Collectors.toList()).containsAll(Arrays.asList(Long.valueOf(onlineDriver), 6L, 4L)));
        Assert.assertTrue(filteredDrivers.length == 3);
    }

    private void AssertNothingEmpty(final DriverDTO response)
    {
        Assert.assertTrue(Objects.nonNull((response.getUsername())));
        Assert.assertTrue(Objects.nonNull((response.getPassword())));
    }

    private void AssertAreEquals(final DriverDTO response, final DriverDTO driverDTO)
    {
        Assert.assertEquals(response.getId(), driverDTO.getId());
        Assert.assertEquals(response.getUsername(), driverDTO.getUsername());
        Assert.assertEquals((response.getPassword()), driverDTO.getPassword());
    }
}
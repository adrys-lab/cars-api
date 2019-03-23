package com.cars;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cars.controller.ApiVersionController;
import com.cars.controller.car.CarController;
import com.cars.controller.driver.DriverController;
import com.cars.controller.login.LoginController;
import com.cars.datatransferobject.login.AuthenticationRequest;
import com.cars.datatransferobject.login.AuthenticationResponse;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public abstract class CommonPlatformTest extends CommonTest
{


    private String versionMappedPath;
    private String carMappedPath;
    private String driverMappedPath;
    private String loginMappedPath;

    private String tokenHeaderKey  = "Authorization";

    private String tokenBearerValue = "Bearer %s";

    @Autowired
    private MockMvc mvc;

    @Before
    public void config() throws Exception
    {
        this.versionMappedPath = "/" + ApiVersionController.class.getAnnotation(RequestMapping.class).value()[0];
        this.carMappedPath = versionMappedPath + CarController.class.getAnnotation(RequestMapping.class).value()[0];
        this.driverMappedPath = versionMappedPath + DriverController.class.getAnnotation(RequestMapping.class).value()[0];
        this.loginMappedPath = versionMappedPath + LoginController.class.getMethod("login", AuthenticationRequest.class).getAnnotation(PostMapping.class).value()[0];
    }

    protected String getBaseApiMappedPath()
    {
        return versionMappedPath;
    }

    protected String getCarMappedPath()
    {
        return carMappedPath;
    }

    protected String getDriverMappedPath()
    {
        return driverMappedPath;
    }

    protected String getLoginMappedPath()
    {
        return loginMappedPath;
    }

    protected String getTokenHeaderKey() throws Exception {
        return tokenHeaderKey;
    }

    public MockMvc getMvc()
    {
        return mvc;
    }

    public String getWriterBearer() throws Exception
    {
        return obtainBearer(new AuthenticationRequest("user1", "user1"));
    }

    public String getReaderBearer() throws Exception
    {
        return obtainBearer(new AuthenticationRequest("user3", "user3"));
    }

    public String getUnauthorizedBearer() throws Exception
    {
        return obtainBearer(new AuthenticationRequest("user5", "user5"));
    }

    public String obtainBearer(final AuthenticationRequest authenticationRequestUser) throws Exception
    {
        final MvcResult loginResult = doLogin(authenticationRequestUser);
        return getBearer(getToken(loginResult.getResponse().getContentAsString()));
    }

    public String obtainToken(final AuthenticationRequest authenticationRequestUser) throws Exception
    {
        final MvcResult loginResult = doLogin(authenticationRequestUser);
        return getToken(loginResult.getResponse().getContentAsString());
    }

    public MvcResult doLogin(final AuthenticationRequest authenticationRequestUser) throws Exception
    {
        return getMvc().perform(MockMvcRequestBuilders.post("/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(authenticationRequestUser)))
                .andReturn();
    }

    private String getToken(final String loginResult) throws Exception
    {
        String tokenized = modelifyJson(stringifyJson(loginResult), AuthenticationResponse.class).getToken();
        return tokenized.substring(15, tokenized.length()-3);
    }

    private String getBearer(final String token)
    {
        return String.format(tokenBearerValue, token);
    }
}

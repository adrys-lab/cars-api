package com.cars.controller.login;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import com.cars.CommonPlatformTest;
import com.cars.datatransferobject.login.AuthenticationRequest;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginControllerTest extends CommonPlatformTest
{

    @Test
    public void testUnauthorizedUser() throws Exception {
        AuthenticationRequest invalidUser = new AuthenticationRequest("user9", "user9");
        MvcResult result = doLogin(invalidUser);
        Assert.assertEquals(result.getResponse().getStatus(), HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void testAuthorizedUser() throws Exception {
        final String token = obtainToken(new AuthenticationRequest("user1", "user1"));
        Assert.assertTrue(StringUtils.isNotEmpty(token));
    }

    @Test
    public void avoidNewTokensForValidOnes() throws Exception {
        final String token = obtainToken(new AuthenticationRequest("user1", "user1"));
        final String newToken = obtainToken(new AuthenticationRequest("user1", "user1"));
        Assert.assertEquals(token, newToken);
    }
}

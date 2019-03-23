package com.cars.datatransferobject.login;

import java.io.Serializable;

/**
 * Model to bind Authentication Platform requests
 */
public class AuthenticationResponse implements Serializable
{

    private static final long serialVersionUID = 125012308512398713L;

    private final String token;

    public AuthenticationResponse(String token)
    {
        this.token = token;
    }

    public String getToken()
    {
        return this.token;
    }
}

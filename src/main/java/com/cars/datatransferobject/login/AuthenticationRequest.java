package com.cars.datatransferobject.login;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Model to bind Authentication Platform requests
 */
public class AuthenticationRequest implements Serializable
{

    private static final long serialVersionUID = -926512398731254778L;

    @NotNull(message = "User Username is a mandatory field.")
    private String username;

    @NotNull(message = "User Password is a mandatory field.")
    private String password;

    public AuthenticationRequest()
    {
        super();
    }

    public AuthenticationRequest(String username, String password)
    {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}

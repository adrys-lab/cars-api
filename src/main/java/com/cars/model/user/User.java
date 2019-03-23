package com.cars.model.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Model to map User usage under Spring Security framework by implementing UserDetails
 */
public class User implements UserDetails, Serializable
{

    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public User(Long id, String username, String password, List<GrantedAuthority> roles)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = roles;
    }

    @JsonIgnore
    public Long getId()
    {
        return id;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired()

    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }
}

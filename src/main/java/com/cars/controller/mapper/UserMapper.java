package com.cars.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.cars.domainobject.user.RoleDO;
import com.cars.domainobject.user.UserDO;
import com.cars.model.user.User;

public final class UserMapper
{

    public static User create(UserDO userDO)
    {
        return new User(
                userDO.getId(),
                userDO.getUsername(),
                userDO.getPassword(),
                mapToGrantedAuthorities(userDO.getRoleDOs())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(final List<RoleDO> roles)
    {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());
    }
}

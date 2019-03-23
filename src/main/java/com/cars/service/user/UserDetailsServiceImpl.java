package com.cars.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cars.controller.mapper.UserMapper;
import com.cars.dataaccessobject.UserRepository;
import com.cars.domainobject.user.UserDO;

/**
 * Service implementing Spring Security User Details Service to retrieve users.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{

    @Autowired
    private UserRepository userRepository;

    /**
     * Add Users cacheable -> Cache 'users'
     */
    @Override
    @Cacheable(value = "users")
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
    {
        final UserDO userDO = userRepository.findByUsername(username);
        if (userDO == null)
        {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return UserMapper.create(userDO);
        }
    }
}

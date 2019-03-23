package com.cars.controller.login;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cars.controller.ApiVersionController;
import com.cars.datatransferobject.login.AuthenticationRequest;
import com.cars.datatransferobject.login.AuthenticationResponse;
import com.cars.platform.security.Tokenizer;

@RestController
public class LoginController extends ApiVersionController
{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Tokenizer tokenizer;

    @Autowired
    private UserDetailsService userDetailsService;

    @Description(value = "Operation to get an authenticated and valid Token Login for the other API Methods.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@NotNull @RequestBody final AuthenticationRequest authenticationRequest)
    {
        doContextAuthenticate(authenticationRequest);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = tokenizer.buildToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    private void doContextAuthenticate(final AuthenticationRequest authenticationRequest)
    {
        final Authentication authentication = getAuthentication(authenticationRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication getAuthentication(final AuthenticationRequest authenticationRequest)
    {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
    }
}

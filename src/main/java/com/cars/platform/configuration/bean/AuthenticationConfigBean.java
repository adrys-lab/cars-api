package com.cars.platform.configuration.bean;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ConfigurationProperties(prefix="cars.authentication")
public class AuthenticationConfigBean {

    private String tokenHeaderKey;
    private String bearerHeader;
    private String secret;
    private Long expiration;

    public String getTokenHeaderKey() {
        return tokenHeaderKey;
    }

    public String getBearerHeader() {
        return bearerHeader;
    }

    public String getSecret() {
        return secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setTokenHeaderKey(String tokenHeaderKey) {
        this.tokenHeaderKey = tokenHeaderKey;
    }

    public void setBearerHeader(String bearerHeader) {
        this.bearerHeader = bearerHeader;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}

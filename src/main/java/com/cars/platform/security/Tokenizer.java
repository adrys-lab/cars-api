package com.cars.platform.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cars.model.exception.internal.login.LoginTokenExpired;
import com.cars.platform.configuration.bean.AuthenticationConfigBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Injectable Tokenizer used to handle Authentication Token management
 */
@Component
public class Tokenizer implements Serializable {

    private static final long serialVersionUID = -12390814512415L;

    private static final String TOKENS_CACHE_NAME = "tokens";
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    /**
     * Add a cache flow to avoid overload server login attempts:
     * if a token already exists (and is valid) for the requester User, return it.
     * If not, reload token and save it in the cache.
     */
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private AuthenticationConfigBean authenticationConfigBean;

    public String buildToken(UserDetails userDetails)
    {
        final String existingToken = getTokenFromCache(userDetails.getUsername());
        if (StringUtils.isNotBlank(existingToken) && isValidToken(existingToken))
        {
            return existingToken;
        }
        final Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return buildToken(claims);
    }


    public String getUsernameFromToken(final String token)
    {
        try {
            final Claims claims = getClaimsFromToken(token);
            return (Optional.of(claims).orElse(Jwts.claims())).getSubject();
        } catch (ExpiredJwtException e)
        {
            throw new LoginTokenExpired();
        }
    }


    public Boolean isValidToken(final String token)
    {
        final Date expiration = extractTokenExpirationDate(token);
        return expiration.after(new Date());
    }


    private Claims getClaimsFromToken(String token)
    {
        return Optional.
                ofNullable(Jwts.parser()
                        .setSigningKey(authenticationConfigBean.getSecret())
                        .parseClaimsJws(token)
                        .getBody())
                .orElse(Jwts.claims());
    }

    private String buildToken(Map<String, Object> claims)
    {
        final String token =
                Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, authenticationConfigBean.getSecret())
                .compact();
        putTokenToCache((String) claims.get(CLAIM_KEY_USERNAME), token);
        return token;
    }


    private Date extractTokenExpirationDate(String token)
    {
        final Claims claims = getClaimsFromToken(token);
        return Optional.ofNullable(claims.getExpiration()).orElse(new Date());
    }


    private Date generateExpirationDate()
    {
        return new Date(System.currentTimeMillis() + authenticationConfigBean.getExpiration());
    }


    private String getTokenFromCache(final String username)
    {
        final Cache.ValueWrapper cacheValue = getTokensCache().get(username);
        if (Optional.ofNullable(cacheValue).isPresent())
        {
            return (String) cacheValue.get();
        }
        return StringUtils.EMPTY;
    }


    private void putTokenToCache(final String username, final String token)
    {
        getTokensCache().put(username, token);
    }


    private Cache getTokensCache()
    {
        return cacheManager.getCache(TOKENS_CACHE_NAME);
    }
}
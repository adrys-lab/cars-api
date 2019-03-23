package com.cars.platform.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cars.datatransferobject.error.ErrorInfo;
import com.cars.model.exception.internal.ApiInternalException;
import com.cars.platform.configuration.bean.AuthenticationConfigBean;
import com.cars.platform.security.Tokenizer;

/**
 * Filter to intercepts all requests to get authentication headers and handle authentication under Spring Context.
 * Is pretended to implement Filter solution to not validate authentication patams in all REST API Methods.
 */
public class AuthenticationFilter extends OncePerRequestFilter
{

    private static final Log LOGGER = LogFactory.getLog(AuthenticationFilter.class);

    private static final String DEFAULT_FATAL_ERROR_MSG = "Oops ! There's a fatal error in our platform. Try again later.";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Tokenizer tokenizer;

    @Autowired
    private AuthenticationConfigBean authenticationConfigBean;

    @Override
    protected void doFilterInternal(@NotNull final HttpServletRequest request, @NotNull final HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authBearer = StringUtils.defaultIfBlank(request.getHeader(authenticationConfigBean.getTokenHeaderKey()), StringUtils.EMPTY);
        LOGGER.info(String.format("Attempt to acces with token %s", authBearer));

        try {
            if (StringUtils.startsWith(authBearer, authenticationConfigBean.getBearerHeader()) && authBearer.length() > 7)
            {
                final String authToken = StringUtils.substring(authBearer, 7);
                final String username = tokenizer.getUsernameFromToken(authToken);
                if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null && tokenizer
                        .isValidToken(authToken))
                {
                    final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    LOGGER.info(String.format("User Authenticated: %s", username));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(request, response);

        /*
         * Add a Filter catch exceptions occurred during the filtering chain engine and servlet engine.
         * It's important catch exceptions after chain.doFilter, in order to STOP Filter chain execution and throw our exception.
         */
        } catch (final ApiInternalException e)
        {
            final ResponseStatus annotatedException = e.getClass().getAnnotation(ResponseStatus.class);
            final ErrorInfo errorInfo = new ErrorInfo(annotatedException.reason(), e.getDetail(), annotatedException.code().value());
            doResponse(response, errorInfo);
        } catch (final Exception e)
        {
            final ErrorInfo errorInfo = new ErrorInfo(DEFAULT_FATAL_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR.value());
            doResponse(response, errorInfo);
        }
    }

    private void doResponse(final HttpServletResponse response, final ErrorInfo errorInfo) throws IOException
    {
        response.setStatus(errorInfo.getCode());
        response.getWriter().write(convertObjectToJson(errorInfo.getMessage()));
    }

    private String convertObjectToJson(final Object object) throws JsonProcessingException
    {
        if (object == null)
        {
            return null;
        }
        return new ObjectMapper().writeValueAsString(object);
    }
}
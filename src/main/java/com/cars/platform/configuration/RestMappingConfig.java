package com.cars.platform.configuration;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/*
 * Mapping config to allow inject our HierarchicRestMappingPaths
 */
@Configuration
public class RestMappingConfig extends DelegatingWebMvcConfiguration
{

    @Configuration
    public static class AutoConfiguration extends WebMvcAutoConfiguration
    {
    }

    /*
     * here set our HierarchicRestMappingPaths
     */
    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping()
    {
        return new HierarchicRestMappingPaths();
    }

    @Bean
    @Primary
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping()
    {
        return super.requestMappingHandlerMapping();
    }

}

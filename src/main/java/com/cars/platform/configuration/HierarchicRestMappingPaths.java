package com.cars.platform.configuration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/*
 * Request Mapping Handler to allow Rest Controller paths inheritance such as:
 *
 * - ParentController -> Mapped (/application)
 * -- Controller 1 -> Inherits ParentController -> Mapped(/controller1) ---> Mapping Result (/application/controller1)
 * -- Controller 2 -> Inherits ParentController -> Mapped(/controller2) ---> Mapping Result (/application/controller2)
 */
class HierarchicRestMappingPaths extends RequestMappingHandlerMapping
{

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType)
    {
        RequestMappingInfo methodMapping = super.getMappingForMethod(method, handlerType);
        if (methodMapping == null)
            return null;
        List<String> superclassUrlPatterns = new ArrayList<String>();
        boolean springPath = false;
        for (Class<?> clazz = handlerType; clazz != Object.class; clazz = clazz.getSuperclass())
        {
            if (clazz.isAnnotationPresent(RequestMapping.class))
            {
                if (springPath)
                {
                    superclassUrlPatterns.add(clazz.getAnnotation(RequestMapping.class).value()[0]);
                } else {
                    springPath = true;
                }
            }
        }
        if (!superclassUrlPatterns.isEmpty())
        {
            RequestMappingInfo superclassRequestMappingInfo = new RequestMappingInfo("",
                    new PatternsRequestCondition(String.join("", superclassUrlPatterns)), null, null, null, null, null, null);
            return superclassRequestMappingInfo.combine(methodMapping);
        } else {
            return methodMapping;
        }
    }
}
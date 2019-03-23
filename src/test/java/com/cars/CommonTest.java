package com.cars;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonTest
{


    protected static String stringifyJson(final Object obj)
    {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    protected static <T> T modelifyJson(final File jsonFile, final Class<T> clazz) throws Exception
    {
        return new ObjectMapper().readValue(jsonFile, clazz);
    }

    protected static <T> T modelifyJson(final String jsonString, final Class<T> clazz) throws Exception
    {
        return new ObjectMapper().readValue(jsonString, clazz);
    }
}

package com.cars.datatransferobject.error;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/*
* Class to wrapp internal checked exceptions for the end-user
*/
public class ErrorInfo implements Serializable
{

    private static final long serialVersionUID = -92651231312091234L;

    private final String message;
    private final String detail;
    private final Integer code;

    public ErrorInfo(final String message, final String detail, final Integer code)
    {
        this.message = message;
        this.detail = detail;
        this.code = code;
    }

    public ErrorInfo(final String message, final Integer code)
    {
        this.message = message;
        this.detail = StringUtils.EMPTY;
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public String getDetail()
    {
        return detail;
    }

    public Integer getCode()
    {
        return code;
    }
}

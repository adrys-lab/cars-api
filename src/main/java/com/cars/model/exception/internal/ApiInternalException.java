package com.cars.model.exception.internal;

import org.apache.commons.lang3.StringUtils;

/*
* Checked Exceptions with only internal visibility -> not for end-user
*/
public class ApiInternalException extends RuntimeException
{

    private final String detail;

    public ApiInternalException()
    {
        this.detail = StringUtils.EMPTY;
    }

    public ApiInternalException(final String detail)
    {
        this.detail = detail;
    }

    public String getDetail()
    {
        return StringUtils.defaultIfBlank(detail, StringUtils.EMPTY);
    }
}

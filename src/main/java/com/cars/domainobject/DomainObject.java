package com.cars.domainobject;

/*
* Interface implemented by all DO's.
* It contains basic common methods.
*/
public interface DomainObject
{

    Long getId();
    void setId(final Long id);
    void setDeleted(boolean deleted);
}

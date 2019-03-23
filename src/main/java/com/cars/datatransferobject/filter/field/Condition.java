package com.cars.datatransferobject.filter.field;

/*
* Condition Class wrapped into FilterField.
* It indicates the Comparison Condition that user whants to do.
* It has it's translation in query (querify)
*/
public enum Condition
{

    EQUALS("="),
    NOT_EQUALS("!="),
    HIGHER(">"),
    HIGHER_OR_EQUALS(">="),
    LOWER("<"),
    LOWER_OR_EQUALS("<="),
    LIKE("LIKE");

    private final String queryfied;

    Condition(String queryfied)
    {
        this.queryfied = queryfied;
    }

    public String getQueryfied()
    {
        return queryfied;
    }
}

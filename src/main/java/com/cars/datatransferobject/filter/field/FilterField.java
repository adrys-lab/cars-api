package com.cars.datatransferobject.filter.field;

/*
* Class which wrapps the requested value and comparison condition.
* It's generics is the Class Type of the field value (String, Boolean, Number....)
*/
public class FilterField<T>
{

    private Condition condition;
    private T value;

    public FilterField(final Condition condition, final T value)
    {
        this.condition = condition;
        this.value = value;
    }

    public FilterField()
    {
    }

    public Condition getCondition()
    {
        return condition;
    }

    public T getValue()
    {
        return value;
    }

    public T getQuerifiedValue()
    {
        return value;
    }
}

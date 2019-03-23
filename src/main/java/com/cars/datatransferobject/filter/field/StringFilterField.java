package com.cars.datatransferobject.filter.field;

/*
* Concrete FilterField for Strings.
* Needs a concrete class because it's querified value needs a '' enclosing.
*/
public class StringFilterField extends FilterField<String>
{

    public StringFilterField()
    {
    }

    public StringFilterField(final Condition condition, final String value)
    {
        super(condition, value);
    }

    public String getQuerifiedValue()
    {
        return "'" + getValue() + "'";
    }

}

package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue{
    public String value;
    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    public boolean equals(Object another) {
        if(!(another instanceof StringValue)) {
            return false;
        }

        StringValue otherString = (StringValue) another;
        return this.value.equals(otherString.value);
    }

}

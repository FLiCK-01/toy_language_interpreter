package model.values;

import model.types.IType;
import model.types.BoolType;

public class BoolValue implements IValue {
    private boolean val;
    public BoolValue(boolean v) {
        this.val = v;
    }
    public boolean getVal() {
        return val;
    }
    public String toString() {
        return String.valueOf(val);
    }
    public IType getType() {
        return new BoolType();
    }

    public boolean equals(Object another) {
        if(!(another instanceof BoolValue)) {
            return false;
        }

        BoolValue otherBool = (BoolValue) another;
        return this.val == otherBool.val;
    }
}

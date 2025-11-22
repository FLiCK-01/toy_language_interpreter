package model.values;

import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue {
    private int val;
    public IntValue(int v) {
        this.val = v;
    }
    public int getVal() {
        return val;
    }
    public String toString() {
        return String.valueOf(val);
    }
    public IType getType() {
        return new IntType();
    }
    public boolean equals(Object another) {
        if(!(another instanceof IntValue)) {
            return false;
        }

        IntValue otherInt = (IntValue) another;
        return this.val == otherInt.val;
    }
}

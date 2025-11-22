package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue {
    int address;
    IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public boolean equals(Object another) {
        if (!(another instanceof RefValue)) {
            return false;
        } else {
            RefValue otherRef = (RefValue) another;
            return this.address == otherRef.address && this.locationType == otherRef.locationType;
        }
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }
    public int getAddr() {
        return address;
    }
}

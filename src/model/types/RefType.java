package model.types;

import model.values.IValue;
import model.values.RefValue;

public class RefType implements IType{
    IType inner;
    public RefType(IType inner) {
        this.inner = inner;
    }
    IType getInner() {
        return inner;
    }
    public boolean equals(Object another) {
        if(another instanceof RefType) {
            return inner.equals(((RefType)another).getInner());
        }
        else return false;
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }
}

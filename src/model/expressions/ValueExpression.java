package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.values.IValue;

public class ValueExpression implements IExp {
    private IValue val;
    public ValueExpression(IValue val) {
        this.val = val;
    }

    public IValue eval(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException {
        return val;
    }

    @Override
    public IExp deepCopy() {
        return new ValueExpression(this.val);
    }

    @Override
    public String toString() {
        return "ValueExpression{" + "val=" + val + '}';
    }
}

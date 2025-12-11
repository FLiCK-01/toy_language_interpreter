package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.values.IValue;

public class VariableExpression implements IExp {
    private final String id;
    public VariableExpression(String id) {
        this.id = id;
    }

    public IValue eval(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException {
        return table.get(id);
    }

    @Override
    public IExp deepCopy() {
        return new VariableExpression(this.id);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv.get(id);
    }

    @Override
    public String toString() {
        return "VariableExpression{" + "id=" + id + '}';
    }
}

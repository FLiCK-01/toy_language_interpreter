package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.values.IValue;

public class VariableExpression implements IExp {
    private final String id;
    public VariableExpression(String id) {
        this.id = id;
    }

    public IValue eval(MyIDictionary<String, IValue> table) throws MyException {
        return table.get(id);
    }

    @Override
    public IExp deepCopy() {
        return new VariableExpression(this.id);
    }

    @Override
    public String toString() {
        return "VariableExpression{" + "id=" + id + '}';
    }
}

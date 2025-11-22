package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExpression implements IExp{
    private IExp exp1;
    private IExp exp2;
    private RelationalOperator operator;

    public RelationalExpression(IExp exp1, IExp exp2, RelationalOperator operator) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = operator;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> table) throws MyException {
        IValue v1, v2;
        v1 = exp1.eval(table);

        if(!v1.getType().equals(new IntType())) {
            throw new MyException("First operand is not of int type.");
        }

        v2 = exp2.eval(table);
        if(!v2.getType().equals(new IntType())) {
            throw new MyException("Second operand is not of int type.");
        }

        int n1 = ((IntValue) v1).getVal();
        int n2 = ((IntValue) v2).getVal();

        switch (operator) {
            case LESS_THAN:
                return new BoolValue(n1 < n2);
            case LESS_EQUAL:
                return new BoolValue(n1 <= n2);
            case EQUAL:
                return new BoolValue(n1 == n2);
            case NOT_EQUAL:
                return new BoolValue(n1 != n2);
            case GREATER_THAN:
                return new BoolValue(n1 > n2);
            case GREATER_EQUAL:
                return new BoolValue(n1 >= n2);
            default:
                throw new MyException("Invalid relational operator.");
        }
    }

    public IExp deepCopy() {
        return new RelationalExpression(this.exp1, this.exp2, this.operator);
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + operator.toString() + " " + exp2.toString();
    }
}

package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
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
    public IValue eval(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException {
        IValue v1, v2;
        v1 = exp1.eval(table, heap);

        if(!v1.getType().equals(new IntType())) {
            throw new MyException("First operand is not of int type.");
        }

        v2 = exp2.eval(table, heap);
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
    @Override
    public IExp deepCopy() {
        return new RelationalExpression(exp1.deepCopy(), exp2.deepCopy(), operator);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType v1 = exp1.typeCheck(typeEnv);
        IType v2 = exp2.typeCheck(typeEnv);

        if(v1.equals(new IntType())) {
            if(v2.equals(new IntType())) {
                return new IntType();
            } else throw new MyException("second operand is not an integer");
        } else throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + operator.toString() + " " + exp2.toString();
    }
}

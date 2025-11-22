package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithmeticalExpression implements IExp{
    private IExp exp1;
    private IExp exp2;
    private ArithmeticalOperators operator;

    public ArithmeticalExpression(IExp exp1, IExp exp2, ArithmeticalOperators operator){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = operator;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> table) throws MyException {
        IValue v1;
        IValue v2;
        v1=exp1.eval(table);
        if(v1.getType().equals(new IntType())) {
            v2 = exp2.eval(table);
            if(v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if(operator.equals(ArithmeticalOperators.ADD)) return new IntValue(n1 + n2);
                else if(operator.equals(ArithmeticalOperators.SUBTRACT)) return new IntValue(n1 - n2);
                else if(operator.equals(ArithmeticalOperators.MULTIPLY)) return new IntValue(n1 * n2);
                else{
                    if(n2 != 0) return new IntValue(n1 / n2);
                    else throw new MyException("Division by zero");
                }
            } else throw new MyException("second operand is not an integer");
        } else throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + operator + " " + exp2.toString();
    }
}
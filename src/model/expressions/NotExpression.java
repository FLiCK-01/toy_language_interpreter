package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

import java.beans.Expression;

public class NotExpression implements IExp {
    private IExp exp;

    public NotExpression(IExp exp) {
        this.exp = exp;
    }
    @Override
    public IValue eval(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException {
        IValue val = exp.eval(table, heap);
        if(!val.getType().equals(new BoolType())){
            throw new MyException("logical operand not boolean");
        }
        boolean b = ((BoolValue) val).getVal();

        return new BoolValue(!b);
    }

    @Override
    public IExp deepCopy() {
        return new NotExpression(exp.deepCopy());
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type = exp.typeCheck(typeEnv);
        if(type.equals(new BoolType())){
            return new BoolType();
        }
        else {
            throw new MyException("logical operand not boolean");
        }
    }

    @Override
    public String toString() {
        return "!(" + exp.toString() + ")";
    }
}

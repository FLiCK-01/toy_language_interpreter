package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicalExpression implements IExp {
    private IExp left;
    private IExp right;
    private LogicalOperation op;

    public LogicalExpression(IExp left, IExp right, LogicalOperation op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException {
        IValue exp1 = left.eval(table, heap);
        IValue exp2 = right.eval(table, heap);
        if(exp1.getType().equals(new BoolType()) && exp2.getType().equals(new BoolType())) {
            BoolValue aux1 = (BoolValue) exp1;
            BoolValue aux2 = (BoolValue) exp2;
            if(op.equals(LogicalOperation.AND)) {
                return new BoolValue(aux1.getVal() && aux2.getVal());
            } else{
                return new BoolValue(aux1.getVal() || aux2.getVal());
            }
        } else {
            throw new MyException("At least one of the expressions is not boolean.");
        }
    }

    @Override
    public IExp deepCopy() {
        return new LogicalExpression(left.deepCopy(), right.deepCopy(), op);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type1 = left.typeCheck(typeEnv);
        IType type2 = right.typeCheck(typeEnv);

        if(type1.equals(new BoolType())) {
            if(type2.equals(new BoolType())) {
                return new BoolType();
            } else throw new MyException("second operand is not boolean");
        } else throw new MyException("first operand is not boolean");
    }

    @Override
    public String toString() {
        return "LogicalExpression{" + "left=" + left + ", right=" + right + ", op=" + op + '}';
    }
}

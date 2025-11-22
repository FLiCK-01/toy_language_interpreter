package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.types.BoolType;
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
    public IValue eval(MyIDictionary<String, IValue> table) throws MyException {
        IValue exp1 = left.eval(table);
        IValue exp2 = right.eval(table);
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
    public String toString() {
        return "LogicalExpression{" + "left=" + left + ", right=" + right + ", op=" + op + '}';
    }
}

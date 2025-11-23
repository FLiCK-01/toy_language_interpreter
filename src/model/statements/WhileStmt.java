package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStmt implements IStmt{
    private IExp exp;
    private IStmt stmt;

    public WhileStmt(IExp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        IValue conditionVal = exp.eval(state.getSymTable(), state.getHeap());

        if(!conditionVal.getType().equals(new BoolType())) {
            throw new MyException("Condition not of bool type.");
        }

        BoolValue boolVal = (BoolValue) conditionVal;
        if(boolVal.getVal()) {
            stack.push(this);
            stack.push(stmt);
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "while(" + exp.toString() + ") { " + stmt.toString() + " }";
    }
}

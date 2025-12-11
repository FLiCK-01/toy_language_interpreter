package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.values.IValue;

public class PrintStmt implements IStmt{
    IExp exp;

    public PrintStmt(IExp exp){
        this.exp = exp;
    }
    @Override
    public String toString() {
        return "print(" + exp.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIHeap heap = state.getHeap();
        state.getOut().add(exp.eval(state.getSymTable(), heap));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(this.exp);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }
}

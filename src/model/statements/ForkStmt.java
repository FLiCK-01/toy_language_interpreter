package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.adt.MyStack;

public class ForkStmt implements IStmt{
    private final IStmt inner;

    public ForkStmt(IStmt inner) {
        this.inner = inner;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return new PrgState(new MyStack<>(),state.getSymTable().deepCopy(),state.getOut(),inner,state.getFileTable(),state.getHeap());
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(inner.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + inner.toString() + ")";
    }
}

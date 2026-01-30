package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnlockStmt implements IStmt {
    private String var;
    private static Lock lock = new ReentrantLock();

    public UnlockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();

        try {
            IValue val = state.getSymTable().get(var);
            if(val == null || !val.getType().equals(new IntType())) throw new MyException("Unlock: variable is not of type int");

            int foundIndex = ((IntValue) val).getVal();
            if(!state.getLockTable().containsKey(foundIndex)) throw new MyException("Unlock: variable does not exist");

            if(state.getLockTable().get(foundIndex) == state.getId()) {
                state.getLockTable().update(foundIndex, -1);
            }
        } finally {
            lock.unlock();
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new UnlockStmt(var);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if(!typeEnv.get(var).equals(new IntType())) throw new MyException("variable is not of type int.");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "unlock(" + var + ")";
    }
}

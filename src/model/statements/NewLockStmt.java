package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.types.IType;
import model.types.IntType;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStmt implements IStmt {
    private String var;
    private static Lock lock = new ReentrantLock();

    public NewLockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();

        try {
            int freeLoc = state.getLockTable().getFreeValue();
            state.getLockTable().put(freeLoc, -1);

            if(state.getSymTable().isDefined(var) && state.getSymTable().get(var).equals(new IntType())) {
                state.getSymTable().put(var, new IntValue(freeLoc));
            } else {
                throw new MyException("variable not declared or not of type int.");
            }
        } finally {
            lock.unlock();
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLockStmt(var);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if(!typeEnv.get(var).equals(new IntType())) {
            throw new MyException("variable must be of type int.");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "newLock(" + var + ")";
    }
}

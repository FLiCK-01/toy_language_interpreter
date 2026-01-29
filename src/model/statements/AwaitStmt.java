package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.types.IType;
import model.types.IntType;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt {
    private String var;
    private static Lock lock = new ReentrantLock();

    public AwaitStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();

        try {
            if(!state.getSymTable().isDefined(var)) throw new MyException("Await: variable not defined");

            int foundIndex = ((IntValue)state.getSymTable().get(var)).getVal();
            if(!state.getLatchTable().containsKey(foundIndex)) throw new MyException("Await: index not found in LatchTable");

            if(state.getLatchTable().get(foundIndex) > 0) {
                state.getExeStack().push(this);
            }
        } finally {
            lock.unlock();
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if(typeEnv.get(var).equals(new IntType())) return typeEnv;
        else throw new MyException("Await: var must be of type int.");
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }
}

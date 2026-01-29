package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.types.IType;
import model.types.IntType;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStmt implements IStmt {
    private String var;
    private static Lock lock = new ReentrantLock();

    public CountDownStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();

        try {
            if(!state.getSymTable().isDefined(var)) throw new MyException("CountDown: variable not defined");

            int foundIndex = ((IntValue)state.getSymTable().get(var)).getVal();

            if(!state.getLatchTable().containsKey(foundIndex)) throw new MyException("CountDown: index not in LatchTable");

            int currentVal = state.getLatchTable().get(foundIndex);
            if(currentVal > 0) {
                state.getLatchTable().update(foundIndex, currentVal - 1);
            }

            state.getOut().add(new IntValue(state.getId()));
        } finally {
            lock.unlock();
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CountDownStmt(var);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if(typeEnv.get(var).equals(new IntType())) return typeEnv;
        else throw new MyException("CountDown: variable must be of int type");
    }

    @Override
    public String toString() {
        return "countDown(" + var + ")";
    }
}

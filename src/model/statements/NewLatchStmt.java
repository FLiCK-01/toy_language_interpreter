package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt {
    private String var;
    private IExp exp;
    private static Lock lock = new ReentrantLock();

    public NewLatchStmt(String var, IExp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();

        try {
            IValue val = exp.eval(state.getSymTable(), state.getHeap());
            if(!val.getType().equals(new IntType())) {
                throw new MyException("NewLatch: expression must be of int type.");
            }
            int n1 = ((IntValue) val).getVal();

            int freeLoc = state.getLatchTable().getFreeAddress();
            state.getLatchTable().put(freeLoc, n1);

            if(state.getSymTable().isDefined(var)) {
                state.getSymTable().put(var, new IntValue(freeLoc));
            } else {
                throw new MyException("NewLatch: variable not defined.");
            }
        } finally {
            lock.unlock();
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLatchStmt(var, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if(typeEnv.get(var).equals(new IntType()) && exp.typeCheck(typeEnv).equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException("NewLatch: var and exp must be of type int.");
        }
    }

    @Override
    public String toString() {
        return "newLatch(" + var + ", " + exp + ")";
    }
}

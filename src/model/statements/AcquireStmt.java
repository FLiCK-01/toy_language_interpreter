package model.statements;

import exception.MyException;
import javafx.util.Pair;
import model.PrgState;
import model.adt.MyIDictionary;
import model.types.IType;
import model.types.IntType;
import model.values.IntValue;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStmt implements IStmt {
    private String var;
    private static Lock lock = new ReentrantLock();

    public AcquireStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();

        try {
            if(!state.getSymTable().isDefined(var)) throw new MyException("Acquire: var is not defined");
            int foundIndex = ((IntValue)state.getSymTable().get(var)).getVal();

            if(!state.getSemaphoreTable().containsKey(foundIndex)) throw new MyException("Acquire: index not in SemaphoreTable");

            Pair<Integer, List<Integer>> entry = state.getSemaphoreTable().get(foundIndex);
            int n1 = entry.getKey();
            List<Integer> list1 = entry.getValue();
            int nl = list1.size();

            if(n1 > nl) {
                if(!list1.contains(state.getId())) {
                    list1.add(state.getId());
                    state.getSemaphoreTable().update(foundIndex, new Pair<>(n1, list1));
                }
            } else {
                state.getExeStack().push(this);
            }
        } finally {
            lock.unlock();
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AcquireStmt(var);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if(typeEnv.get(var).equals(new IntType())) return typeEnv;
        else throw new MyException("Acquire: var must be of int type.");
    }

    @Override
    public String toString() {
        return "acquire(" + var + ")";
    }
}

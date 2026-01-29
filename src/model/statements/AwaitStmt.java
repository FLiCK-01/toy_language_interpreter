package model.statements;

import exception.MyException;
import javafx.util.Pair;
import model.PrgState;
import model.adt.MyIBarrierTable;
import model.adt.MyIDictionary;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt {
    public String var;
    private static Lock lock = new ReentrantLock();

    public AwaitStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        try {
            MyIDictionary<String, IValue> symTable = state.getSymTable();
            MyIBarrierTable barrierTable = state.getBarrierTable();

            if(!symTable.isDefined(var)) {
                throw new MyException("Await: variable not found in symTable.");
            }
            IValue val =  symTable.get(var);
            if(!val.getType().equals(new IntType())) {
                throw new MyException("Await: variable is not of type int.");
            }
            int foundIndex = ((IntValue) val).getVal();

            if(!barrierTable.containsKey(foundIndex)) {
                throw new MyException("Await: index not found in barrierTable.");
            }

            Pair<Integer, List<Integer>> barrierEntry = barrierTable.get(foundIndex);
            int n1 = barrierEntry.getKey();
            List<Integer> list1 = barrierEntry.getValue();
            int nl = list1.size();

            if(n1 > nl) {
                if(list1.contains(state.getId())) {
                    state.getExeStack().push(this);
                } else {
                    list1.add(state.getId());
                    barrierTable.update(foundIndex, new Pair<>(n1, list1));
                    state.getExeStack().push(this);
                }
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
        if(typeEnv.get(var).equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException("Await: variable is not of type int.");
        }
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }
}

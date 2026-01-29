package model.statements;

import exception.MyException;
import javafx.util.Pair;
import model.PrgState;
import model.adt.MyIBarrierTable;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStmt implements IStmt {
    private String var;
    private IExp exp;
    private static Lock lock = new ReentrantLock();

    public NewBarrierStmt(String var, IExp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        try {
            MyIDictionary<String, IValue> symTable = state.getSymTable();
            MyIHeap heap = state.getHeap();
            MyIBarrierTable barrierTable = state.getBarrierTable();

            IValue val = exp.eval(symTable, heap);
            if(!val.getType().equals(new IntType())) {
                throw new MyException("NewBarrier Expression is not an integer.");
            }
            int number = ((IntValue) val).getVal();

            int freeAddress = barrierTable.getFreeAddress();
            barrierTable.put(freeAddress, new Pair<>(number, new ArrayList<>()));

            if(symTable.isDefined(var) && symTable.get(var).getType().equals(new IntType())) {
                symTable.put(var, new IntValue(freeAddress));
            } else {
                throw new MyException("Variable " + var + " is not defined or not of int type.");
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewBarrierStmt(var, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.get(var);
        IType typeExp = exp.typeCheck(typeEnv);
        if(typeVar.equals(new IntType()) &&  typeExp.equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException("NewBarrier: both variable and expression must be of type int.");
        }
    }

    @Override
    public String toString() {
        return "newBarrier(" + var + ", " + exp + ")";
    }
}

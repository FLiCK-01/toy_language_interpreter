package model.statements;

import exception.MyException;
import javafx.util.Pair;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStmt implements IStmt {
    private String var;
    private IExp exp;
    private static Lock lock = new ReentrantLock();

    public CreateSemaphoreStmt(String var, IExp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();

        try {
            IValue val = exp.eval(state.getSymTable(), state.getHeap());
            if(!val.getType().equals(new IntType())) {
                throw new MyException("CreateSemaphore: expression must be int type");
            }

            int number = ((IntValue) val).getVal();

            int freeLoc = state.getSemaphoreTable().getFreeAddress();
            state.getSemaphoreTable().put(freeLoc, new Pair<>(number, new ArrayList<>()));

            if(state.getSymTable().isDefined(var) && state.getSymTable().get(var).getType().equals(new IntType())) {
                state.getSymTable().put(var, new IntValue(freeLoc));
            } else {
                throw new MyException("CreateSemaphore: variable not defined or not of int type");
            }
        } finally {
            lock.unlock();
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CreateSemaphoreStmt(var, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if(typeEnv.get(var).equals(new IntType()) && exp.typeCheck(typeEnv).equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException("CreateSemaphore: variable and expression must be of type int");
        }
    }

    @Override
    public String toString() {
        return "createSemaphore(" + var + ", " + exp + ")";
    }
}

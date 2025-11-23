package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;
import model.expressions.IExp;

public class IfStmt implements IStmt {
    private IExp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(IExp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }
    public String toString() {
        return "if(" +exp+"){"+thenS+"}else{"+elseS+"}";
    }
    public PrgState execute(PrgState state) throws MyException {
        MyIHeap heap = state.getHeap();
        IValue val = exp.eval(state.getSymTable(), heap);
        if (val.getType().equals(new BoolType())) {
            BoolValue b = (BoolValue) val;
            if(b.getVal()) {
                state.getExeStack().push(thenS);
            } else {
                state.getExeStack().push(elseS);
            }
        } else {
            throw new MyException("The condition is not boolean type.");
        }
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp, thenS.deepCopy(), elseS.deepCopy());
    }
}

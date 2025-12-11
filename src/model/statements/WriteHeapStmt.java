package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class WriteHeapStmt implements IStmt{
    private String varName;
    private IExp exp;

    public WriteHeapStmt(String varName, IExp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();

        if(!symTable.isDefined(varName)) {
            throw new MyException("Variable " + varName + " is not defined");
        }

        IValue value = symTable.get(varName);
        if(!(value.getType() instanceof RefType)) {
            throw new MyException("Variable " + varName + " is not of type RefType");
        }

        RefValue refValue = (RefValue) value;
        int address = refValue.getAddr();
        if(!heap.isDefined(address)) {
            throw new MyException("Address " + address + " is not defined");
        }

        IValue evaluated = exp.eval(symTable, heap);
        RefType refType = (RefType) value.getType();
        IType locationType = refType.getInner();

        if(!evaluated.getType().equals(locationType)) {
            throw new MyException("Expression type does not match variable reference type.");
        }

        heap.update(address, evaluated);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(varName, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type = typeEnv.get(varName);
        if(type instanceof RefType) {
            return typeEnv;
        } else throw new MyException("variable is not of ref type");
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp.toString() + ")";
    }
}

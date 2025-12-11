package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class ReadHeapExp implements IExp{
    private IExp exp;

    public ReadHeapExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException {
        IValue val = exp.eval(table, heap);

        if(!(val instanceof RefValue)) {
            throw new MyException("Expression is not a reference!");
        }

        RefValue refVal = (RefValue) val;
        int address = refVal.getAddr();

        if(!heap.isDefined(address)) {
            throw new MyException("Address " + address + " is not defined!");
        }

        return heap.getVal(address);
    }

    @Override
    public IExp deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type = exp.typeCheck(typeEnv);
        if(type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        } else throw new MyException("rH argument is not of ref type");
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }
}

package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.values.IValue;


public interface IExp {
    public IValue eval(MyIDictionary<String, IValue> table, MyIHeap heap) throws MyException;
    IExp deepCopy();
    IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException;
}

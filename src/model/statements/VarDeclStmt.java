package model.statements;

import exception.MyException;
import model.PrgState;
import model.types.*;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.values.IValue;

public class VarDeclStmt implements IStmt {
    String name;
    IType type;

    public VarDeclStmt(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "VarDeclStmt{" + "name=" + name + ", type=" + type + '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        if(symTbl.isDefined(this.name)) throw new MyException("Variable already defined");
        else if (name != null) {
            symTbl.put(name, type.defaultValue());
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(this.name, this.type);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        typeEnv.put(this.name, type);
        return typeEnv;
    }
}

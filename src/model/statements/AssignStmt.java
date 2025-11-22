package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.expressions.ValueExpression;
import model.types.IType;
import model.values.IValue;

public class AssignStmt implements IStmt{
    String id;
    IExp exp;

    public AssignStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTbl = state.getSymTable();

        if(symTbl.isDefined(this.id)) {
            IValue val = exp.eval(symTbl);
            IType typId = symTbl.get(id).getType();
            if(val.getType().equals(typId)) {
                symTbl.put(this.id, val);
            }
            else throw new MyException("declared type of variable "+id+" and type of the assigned expression do not match.");

        }
        else throw new MyException("variable "+id+" is not defined");
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(this.id, this.exp);
    }
}

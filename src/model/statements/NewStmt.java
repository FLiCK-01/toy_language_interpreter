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

public class NewStmt implements IStmt{
    private String var_name;
    private IExp exp;

    public NewStmt(String var_name, IExp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();

        if(!symTable.isDefined(var_name)){
            throw new MyException("Variable "+var_name+" is not defined");
        }

        IValue value = symTable.get(var_name);
        if(!(value.getType() instanceof RefType)) {
            throw new MyException("Variable "+var_name+" is not of type RefType.");
        }

        IValue evaluated = exp.eval(symTable, heap);
        IType locationType = ((RefType) value.getType()).getInner();

        if(!evaluated.getType().equals(locationType)){
            throw new MyException("Expression type does not correspond with type of ref variable " + var_name+ ".");
        }

        int newAddress = heap.allocate(evaluated);
        symTable.put(var_name, new RefValue(newAddress, locationType));

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(var_name, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + var_name + ", " + exp.toString() + ")";
    }
}

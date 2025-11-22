package model.statements;

import exception.MyException;
import model.PrgState;
import model.expressions.IExp;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt{
    private IExp exp;

    public OpenRFile(IExp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue expEval = exp.eval(state.getSymTable());

        if(expEval.getType().equals(new StringType())) {
            StringValue value = (StringValue) expEval;
            if(state.getFileTable().isDefined(value)) {
                throw new MyException("file was already open.");
            } else {
                try {
                    state.getFileTable().put(value, new BufferedReader(new FileReader(value.getValue())));
                } catch (FileNotFoundException e) {
                    throw new MyException(e.getMessage());
                }
            }
        } else {
            throw new MyException("file name must be a string value.");
        }

        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(this.exp);
    }

    @Override
    public String toString() {
        return "OpenRFile{" + "exp=" + exp + '}';
    }
}

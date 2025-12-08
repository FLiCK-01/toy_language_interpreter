package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt{
    private IExp exp;

    public CloseRFile(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap heap = state.getHeap();

        IValue expValue = exp.eval(symTable, heap);
        if(!expValue.getType().equals(new StringType())) {
            throw new MyException("Expression is not of string type.");
        }

        StringValue fileName = (StringValue) expValue;
        if(!fileTable.isDefined(fileName)) {
            throw new MyException("File '" + fileName + "' is not open in the FileTable.");
        }

        BufferedReader bufferedReader = fileTable.get(fileName);
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new MyException("Error while closing file '" + fileName + "'.");
        }

        fileTable.remove(fileName);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(this.exp);
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp.toString() + ")";
    }
}

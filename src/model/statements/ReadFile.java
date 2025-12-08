package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt{
    private IExp exp;
    private String var_name;

    public ReadFile(IExp exp, String var_name){
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap heap = state.getHeap();

        if(!symTable.isDefined(var_name)){
            throw new MyException ("Variable '" + var_name + "' is not defined in the SymTable.");
        }

        IValue varValue = symTable.get(var_name);
        if(!varValue.getType().equals(new IntType())) {
            throw new MyException("Variable '" + var_name + "' is not of type int.");
        }

        IValue expValue = exp.eval(symTable, heap);
        if(!expValue.getType().equals(new StringType())) {
            throw new MyException("Expression of file name does not evaluate to a string type.");
        }

        StringValue fileName = (StringValue) expValue;
        if(!fileTable.isDefined(fileName)){
            throw new MyException ("File '" + fileName + "' is not open in the FileTable.");
        }

        BufferedReader bufferedReader = fileTable.get(fileName);

        try {
            String line = bufferedReader.readLine();
            int intValue;

            if(line == null || line.isEmpty()){
                intValue = 0;
            } else {
                try {
                    intValue = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    throw new MyException ("Line '" + line + "' is not an integer.");
                }
            }

            symTable.put(var_name, new IntValue(intValue));
        } catch(IOException e) {
            throw new MyException ("Error reading file '" + fileName + "'.");
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(this.exp, var_name);
    }

    @Override
    public String toString() {
        return "readFile(" + exp.toString() + ", " + var_name + ")";
    }
}

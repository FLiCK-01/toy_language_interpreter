package model;

import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> out;
    private IStmt originalProgram;
    private MyIDictionary<StringValue, BufferedReader> fileTable;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl, MyIList<IValue> ot, IStmt prg, MyIDictionary<StringValue, BufferedReader> filetbl) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.originalProgram = prg;
        this.fileTable = filetbl;
        this.exeStack.push(prg);
    }

    public MyIList<IValue> getOut() {
        return out;
    }
    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }
    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        return exeStack.toString()+ " " + symTable.toString()+ " " + out.toString();
    }
}

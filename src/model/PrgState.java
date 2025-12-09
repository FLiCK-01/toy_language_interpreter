package model;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
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
    private MyIHeap heap;
    private static int nextId = 0;
    private int id;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl, MyIList<IValue> ot, IStmt prg, MyIDictionary<StringValue, BufferedReader> filetbl, MyIHeap heap) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.originalProgram = prg;
        this.fileTable = filetbl;
        this.heap = heap;
        this.id = getNextId();
        this.exeStack.push(this.originalProgram);
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
    public MyIHeap getHeap() {return heap;}
    public synchronized int getNextId() {return ++this.nextId;}
    public int getId() {return id;}

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty()) {
            throw new MyException("Prg state stack is empty.");
        }

        IStmt stmt = exeStack.pop();
        return stmt.execute(this);
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    @Override
    public String toString() {
        return "id: " + id +"\n" + exeStack.toString()+ " " + symTable.toString()+ " " + out.toString() + " " + heap.toString();
    }
}

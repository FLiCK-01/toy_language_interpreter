package repository;

import exception.RepoException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository implements IRepo {
    private List<PrgState> states;
    private String logFilePath;
    public Repository(String fname) {
        this.logFilePath = fname;
        this.states = new ArrayList<>();

    }

    @Override
    public void addPrgState(PrgState prgState) {
        states.add(prgState);
    }

    @Override
    public void logPrgState(PrgState prgState) throws IOException, RepoException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            PrgState prg = prgState;
            logFile.println("ExeStack: ");
            MyIStack<IStmt> stack = prg.getExeStack();

            for(IStmt stmt : stack.getValues()) {
                logFile.println(stmt.toString());
            }

            logFile.println("SymTable:");
            MyIDictionary<String, IValue> symTable = prg.getSymTable();
            for(Map.Entry<String, IValue> entry : symTable.getContent().entrySet()) {
                logFile.println(entry.getKey() + " ---> " + entry.getValue().toString());
            }

            logFile.println("Out:");
            MyIList<IValue> out = prg.getOut();
            for(IValue val : out.getValues()) {
                logFile.println(val.toString());
            }

            logFile.println("FileTable:");
            MyIDictionary<StringValue, BufferedReader> fileTable = prg.getFileTable();
            for(StringValue key : fileTable.getContent().keySet()) {
                logFile.println(key.toString());
            }

            logFile.println("Heap:");
            MyIHeap heap = prg.getHeap();
            for(Map.Entry<Integer, IValue> entry : heap.getContent().entrySet()) {
                logFile.println(entry.getKey() + " -> " + entry.getValue().toString());
            }

            logFile.println("-------------------------------------------\n");
        } catch (IOException e) {
            throw new RepoException("Error in logfile writing: " + e.getMessage());
        }
    }

    @Override
    public void clearPrgState() {
        states.clear();
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.states;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        this.states = prgList;
    }
}
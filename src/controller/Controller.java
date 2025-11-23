package controller;

import exception.MyException;
import exception.RepoException;
import model.PrgState;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller implements IController {
    private IRepo repo;
    private boolean displayFlag = false;
    public Controller(IRepo repo) {
        this.repo = repo;
    }

    Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream().filter(e -> symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromValues(Collection<IValue> values) {
        return values.stream().filter(v -> v instanceof RefValue).map(v -> {
            RefValue v1 = (RefValue) v;
            return v1.getAddr();
        }).collect(Collectors.toList());
    }

    @Override
    public void oneStep() throws IOException, MyException, RepoException {
        MyIStack<IStmt> exeStack = repo.getCrtPrg().getExeStack();
        IStmt stmt = exeStack.pop();
        stmt.execute(repo.getCrtPrg());
        if(displayFlag) {
            System.out.println("- - - After 1 Step - - -");
            System.out.println(repo.getCrtPrg().toString());
        }
        repo.logPrgState();
    }

    @Override
    public void allStep() throws IOException, MyException, RepoException {
        PrgState prg = repo.getCrtPrg();
        if(displayFlag) {
            System.out.println("- - - Initial State - - -");
            System.out.println(prg.toString());
        }

        repo.logPrgState();
        MyIStack<IStmt> exeStack = prg.getExeStack();

        while(!exeStack.isEmpty()) {
            oneStep();

            prg.getHeap().setContent(safeGarbageCollector(getAddrFromValues(prg.getSymTable().getContent().values()), prg.getHeap().getContent()));
            List<Integer> symTableAddresses = getAddrFromValues(prg.getSymTable().getContent().values());
            List<Integer> heapAddresses = getAddrFromValues(prg.getHeap().getContent().values());
            List<Integer> allActiveAddresses = Stream.concat(symTableAddresses.stream(), heapAddresses.stream()).distinct().collect(Collectors.toList());

            prg.getHeap().setContent(safeGarbageCollector(allActiveAddresses, prg.getHeap().getContent()));

            repo.logPrgState();
        }

        if(displayFlag) {
            System.out.println("- - - Final State - - -");
            System.out.println(prg.toString());
        }
    }

    @Override
    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    @Override
    public boolean getDisplayFlag() {
        return this.displayFlag;
    }

    @Override
    public void addPrgState(PrgState prgState) {
        repo.addPrgState(prgState);
    }

    @Override
    public void clearPrgState() {
        repo.clearPrgState();
    }

    @Override
    public PrgState getCrtProgram() {
        return repo.getCrtPrg();
    }

}

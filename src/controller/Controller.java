package controller;

import exception.MyException;
import exception.RepoException;
import model.PrgState;
import model.adt.MyIStack;
import model.statements.IStmt;
import repository.IRepo;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller implements IController {
    private IRepo repo;
    private boolean displayFlag = false;
    public Controller(IRepo repo) {
        this.repo = repo;
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

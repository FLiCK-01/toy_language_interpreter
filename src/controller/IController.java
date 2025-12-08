package controller;

import exception.MyException;
import exception.RepoException;
import model.PrgState;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IController {
    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException;
    public void allStep() throws InterruptedException, MyException;
    void setDisplayFlag(boolean displayFlag);
    boolean getDisplayFlag();
    void addPrgState(PrgState prgState);
    void clearPrgState();
}

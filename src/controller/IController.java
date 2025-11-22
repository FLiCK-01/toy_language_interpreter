package controller;

import exception.MyException;
import exception.RepoException;
import model.PrgState;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IController {
    void oneStep() throws IOException, MyException, RepoException;
    void allStep() throws IOException, MyException, RepoException;
    void setDisplayFlag(boolean displayFlag);
    boolean getDisplayFlag();
    void addPrgState(PrgState prgState);
    void clearPrgState();
    PrgState getCrtProgram();
}

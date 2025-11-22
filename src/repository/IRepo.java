package repository;
import exception.RepoException;
import model.PrgState;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IRepo {
    PrgState getCrtPrg();
    void addPrgState(PrgState prgState);
    void logPrgState() throws IOException, RepoException;
    void clearPrgState();

}

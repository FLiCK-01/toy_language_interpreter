package repository;
import exception.RepoException;
import model.PrgState;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IRepo {
    void addPrgState(PrgState prgState);
    void logPrgState(PrgState prgState) throws IOException, RepoException;
    void clearPrgState();
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
}

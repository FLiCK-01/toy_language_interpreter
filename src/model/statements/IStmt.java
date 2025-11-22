package model.statements;

import exception.MyException;
import model.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    public IStmt deepCopy();
}

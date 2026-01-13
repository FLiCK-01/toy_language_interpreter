package gui;

import model.statements.IStmt;

public class ExampleWrapper {
    private IStmt statement;
    private String description;

    public ExampleWrapper(IStmt statement, String description) {
        this.statement = statement;
        this.description = description;
    }

    public IStmt getStatement() {
        return statement;
    }

    @Override
    public String toString() {
        return description;
    }
}
import controller.Controller;
import controller.IController;
import exception.MyException;
import model.PrgState;
import model.adt.*;
import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.values.*;
import repository.IRepo;
import repository.Repository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(2))),
                        new PrintStmt(new VariableExpression("v"))));
        PrgState prg1 = createPrgState(ex1);
        IRepo repo1 = new Repository("log1.txt");
        repo1.addPrgState(prg1);
        IController ctr1 = new Controller(repo1);

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithmeticalExpression(new ValueExpression(new IntValue(2)),
                                new ArithmeticalExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), ArithmeticalOperators.MULTIPLY),
                                ArithmeticalOperators.ADD)),
                                new CompStmt(new AssignStmt("b", new ArithmeticalExpression(new VariableExpression("a"),
                                        new ValueExpression(new IntValue(1)), ArithmeticalOperators.ADD)), new PrintStmt(new VariableExpression("b"))))));
        PrgState prg2 = createPrgState(ex2);
        IRepo repo2 = new Repository("log2.txt");
        repo2.addPrgState(prg2);
        IController ctr2 = new Controller(repo2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VariableExpression("a"),
                                        new AssignStmt("v", new ValueExpression(new IntValue(2))),
                                        new AssignStmt("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStmt(new VariableExpression("v"))))));
        PrgState prg3 = createPrgState(ex3);
        IRepo repo3 = new Repository("log3.txt");
        repo3.addPrgState(prg3);
        IController ctr3 = new Controller(repo3);

        IStmt ex4 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenRFile(new VariableExpression("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new CompStmt(
                                                        new ReadFile(new VariableExpression("varf"), "varc"),
                                                        new PrintStmt(new VariableExpression("varc"))
                                                ),
                                                new CompStmt(
                                                        new CompStmt(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new PrintStmt(new VariableExpression("varc"))
                                                        ),
                                                        new CloseRFile(new VariableExpression("varf"))
                                                )
                                        )
                                )
                        )
                )
        );
        PrgState prg4 = createPrgState(ex4);
        IRepo repo4 = new Repository("log4.txt");
        repo4.addPrgState(prg4);
        IController ctr4 = new Controller(repo4);

        IStmt ex5 = new CompStmt(
                new VarDeclStmt("v1", new IntType()),
                new CompStmt(
                        new VarDeclStmt("v2", new IntType()),
                        new CompStmt(
                                new AssignStmt("v1", new ValueExpression(new IntValue(20))),
                                new CompStmt(
                                        new AssignStmt("v2", new ValueExpression(new IntValue(30))),
                                        new IfStmt(
                                                new RelationalExpression(
                                                        new VariableExpression("v1"),
                                                        new VariableExpression("v2"),
                                                        RelationalOperator.LESS_THAN
                                                ),
                                                new PrintStmt(new VariableExpression("v1")),
                                                new PrintStmt(new VariableExpression("v2"))
                                        )
                                )
                        )
                )
        );
        PrgState prg5 = createPrgState(ex5);
        IRepo repo5 = new Repository("log5.txt");
        repo5.addPrgState(prg5);
        IController crt5 = new Controller(repo5);

        TextMenu menu = new TextMenu();
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), crt5));
        menu.addCommand(new ExitCommand("0", "exit"));

        menu.show();
    }

    private static PrgState createPrgState(IStmt originalProgram) {
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();

        return new PrgState(exeStack, symTable, out, originalProgram, fileTable);
    }
}
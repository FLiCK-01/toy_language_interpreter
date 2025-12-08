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

        IStmt ex6 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExpression(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VariableExpression("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ValueExpression(new IntValue(30))),
                                                new NewStmt("v", new ValueExpression(new IntValue(15)))
                                        )
                                )
                        )
                )
        );

        PrgState prg6 = createPrgState(ex6);
        IRepo repo6 = new Repository("log6.txt");
        repo6.addPrgState(prg6);
        IController crt6 = new Controller(repo6);

        IStmt ex7 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExpression(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(0)),
                                                RelationalOperator.GREATER_THAN
                                        ),
                                        new CompStmt(
                                                new PrintStmt(new VariableExpression("v")),
                                                new AssignStmt("v", new ArithmeticalExpression(
                                                        new VariableExpression("v"),
                                                        new ValueExpression(new IntValue(1)),
                                                        ArithmeticalOperators.SUBTRACT
                                                ))
                                        )
                                ),
                                new PrintStmt(new VariableExpression("v"))
                        )
                )
        );

        PrgState prg7 = createPrgState(ex7);
        IRepo repo7 = new Repository("log7.txt");
        repo7.addPrgState(prg7);
        IController ctr7 = new Controller(repo7);

        IStmt ex8 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExpression(new IntValue(10))),
                        new CompStmt(
                                new ForkStmt(
                                        new CompStmt(
                                                new AssignStmt("v", new ValueExpression(new IntValue(30))),
                                                new PrintStmt(new VariableExpression("v"))
                                        )
                                ),
                                new PrintStmt(new VariableExpression("v"))
                        )
                )
        );

        PrgState prg8 = createPrgState(ex8);
        IRepo repo8 = new Repository("log8.txt");
        repo8.addPrgState(prg8);
        IController ctr8 = new Controller(repo8);

        IStmt ex9 = new CompStmt(
                new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("a", new ValueExpression(new IntValue(10))),
                        new CompStmt(
                                new ForkStmt(
                                        new WriteHeapStmt("a", new ValueExpression(new IntValue(20)))
                                ),
                                new PrintStmt(new ReadHeapExp(new VariableExpression("a")))
                        )
                )
        );

        PrgState prg9 = createPrgState(ex9);
        IRepo repo9 = new Repository("log9.txt");
        repo9.addPrgState(prg9);
        IController ctr9 = new Controller(repo9);

        TextMenu menu = new TextMenu();
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), crt5));
        menu.addCommand(new RunExample("6", ex6.toString(), crt6));
        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
        menu.addCommand(new ExitCommand("0", "exit"));

        menu.show();
    }

    private static PrgState createPrgState(IStmt originalProgram) {
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap heap = new MyHeap();

        return new PrgState(exeStack, symTable, out, originalProgram, fileTable, heap);
    }
}
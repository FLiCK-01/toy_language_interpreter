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

        IStmt ex1 = new CompStmt(
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

        PrgState prg1 = createPrgState(ex1);
        IRepo repo1 = new Repository("log1.txt");
        repo1.addPrgState(prg1);
        IController ctr1 = new Controller(repo1);

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("a", new ValueExpression(new IntValue(10))),
                        new CompStmt(
                                new ForkStmt(
                                        new CompStmt(new NewStmt("a", new ValueExpression(new IntValue(50))), new NopStmt())
                                ),
                                new CompStmt(new PrintStmt(new ReadHeapExp(new VariableExpression("a"))), new NopStmt())
                        )
                )
        );

        PrgState prg2 = createPrgState(ex2);
        IRepo repo2 = new Repository("log2.txt");
        repo2.addPrgState(prg2);
        IController ctr2 = new Controller(repo2);

        TextMenu menu = new TextMenu();
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
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
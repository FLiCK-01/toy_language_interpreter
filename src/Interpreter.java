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
        TextMenu menu = new TextMenu();

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
        runTypeCheckAndAdd(menu, "1", ex1, "log1.txt");

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a", new RefType(new BoolType())),
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
        runTypeCheckAndAdd(menu, "2", ex2, "log2.txt");

        menu.addCommand(new ExitCommand("0", "exit"));
        menu.show();
    }

    private static PrgState createPrgState(IStmt originalProgram) {
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap heap = new MyHeap();
        MyIBarrierTable barrierTable = new MyBarrierTable();

        return new PrgState(exeStack, symTable, out, originalProgram, fileTable, heap, barrierTable);
    }

    private static void runTypeCheckAndAdd(TextMenu menu, String key, IStmt stmt, String logFile) {
        try {
            stmt.typeCheck(new MyDictionary<>());

            try (java.io.PrintWriter logWriter = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter(logFile, false)))) {
                logWriter.println("TypeCheck passed successfully for Example " + key);
            } catch (java.io.IOException e) {
                System.out.println("Could not write to log file: " + e.getMessage());
            }

            PrgState prg = createPrgState(stmt);
            IRepo repo = new Repository(logFile);
            repo.addPrgState(prg);
            IController ctr = new Controller(repo);

            menu.addCommand(new RunExample(key, stmt.toString(), ctr));

        } catch (MyException e) {
            System.out.println("Example " + key + " failed type check: " + e.getMessage());
        }
    }
}
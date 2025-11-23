//package view;
//
//import controller.Controller;
//import controller.IController;
//import exception.MyException;
//import exception.RepoException;
//import model.adt.*;
//import model.expressions.*;
//import model.PrgState;
//import model.statements.*;
//import model.types.*;
//import model.values.*;
//import repository.IRepo;
//import repository.Repository;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.Scanner;
//
//public class View implements IView {
//
//    private IController controller;
//    private Scanner scanner;
//
//    public View() {
//        this.controller = null;
//        this.scanner = new Scanner(System.in);
//    }
//
//    @Override
//    public void runMenu() {
//        while (true) {
//            printMenu();
//            System.out.print("Enter your choice: ");
//
//            try {
//                int choice = Integer.parseInt(scanner.nextLine());
//                switch (choice) {
//                    case 1:
//                        selectProgram();
//                        break;
//                    case 2:
//                        runProgram();
//                        break;
//                    case 3:
//                        toggleDisplayFlag();
//                        break;
//                    case 0:
//                        System.out.println("Exiting...");
//                        return;
//                    default:
//                        System.err.println("Invalid choice.");
//                }
//            } catch (NumberFormatException e) {
//                System.err.println("Invalid choice. Please enter a number.");
//            } catch (MyException | RepoException | IOException e) {
//                System.err.println("Execution error: " + e.getMessage());
//                this.controller = null;
//            }
//        }
//    }
//
//    private void printMenu() {
//        System.out.println("\n- - - Menu - - -");
//        System.out.println("1. Select a program.");
//        System.out.println("2. Execute the selected program.");
//
//        String displayFlagStatus = " (N/A - Select a program first)";
//        if (controller != null) {
//            displayFlagStatus = controller.getDisplayFlag() ? "ON" : "OFF";
//        }
//        System.out.println("3. Toggle display flag [Current: " + displayFlagStatus + "]");
//
//        System.out.println("0. Exit");
//    }
//
//    private void selectProgram() {
//
//        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(2))),
//                        new PrintStmt(new VariableExpression("v"))));
//
//        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
//                new CompStmt(new VarDeclStmt("b", new IntType()),
//                        new CompStmt(new AssignStmt("a", new ArithmeticalExpression(new ValueExpression(new IntValue(2)),
//                                new ArithmeticalExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), ArithmeticalOperators.MULTIPLY),
//                                ArithmeticalOperators.ADD)),
//                                new CompStmt(new AssignStmt("b", new ArithmeticalExpression(new VariableExpression("a"),
//                                        new ValueExpression(new IntValue(1)), ArithmeticalOperators.ADD)), new PrintStmt(new VariableExpression("b"))))));
//
//        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
//                new CompStmt(new VarDeclStmt("v", new IntType()),
//                        new CompStmt(new AssignStmt("a", new ValueExpression(new BoolValue(true))),
//                                new CompStmt(new IfStmt(new VariableExpression("a"),
//                                        new AssignStmt("v", new ValueExpression(new IntValue(2))),
//                                        new AssignStmt("v", new ValueExpression(new IntValue(3)))),
//                                        new PrintStmt(new VariableExpression("v"))))));
//
//        IStmt ex4 = new CompStmt(
//                new VarDeclStmt("varf", new StringType()),
//                new CompStmt(
//                        new AssignStmt("varf", new ValueExpression(new StringValue("test.in"))),
//                        new CompStmt(
//                                new OpenRFile(new VariableExpression("varf")),
//                                new CompStmt(
//                                        new VarDeclStmt("varc", new IntType()),
//                                        new CompStmt(
//                                                new CompStmt(
//                                                        new ReadFile(new VariableExpression("varf"), "varc"),
//                                                        new PrintStmt(new VariableExpression("varc"))
//                                                ),
//                                                new CompStmt(
//                                                        new CompStmt(
//                                                                new ReadFile(new VariableExpression("varf"), "varc"),
//                                                                new PrintStmt(new VariableExpression("varc"))
//                                                        ),
//                                                        new CloseRFile(new VariableExpression("varf"))
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//
//        System.out.println("\nSelect program:");
//        System.out.println("1. " + ex1);
//        System.out.println("2. " + ex2);
//        System.out.println("3. " + ex3);
//        System.out.println("4. " + ex4);
//        System.out.print("Enter your choice: ");
//
//        try {
//            int choice2 = Integer.parseInt(scanner.nextLine());
//            IStmt selectedStmt;
//
//            if (choice2 == 1) selectedStmt = ex1;
//            else if (choice2 == 2) selectedStmt = ex2;
//            else if (choice2 == 3) selectedStmt = ex3;
//            else if (choice2 == 4) selectedStmt = ex4;
//            else {
//                System.err.println("Invalid choice.");
//                return;
//            }
//
//            MyIStack<IStmt> exeStack = new MyStack<>();
//            MyIDictionary<String, IValue> symTable = new MyDictionary<>();
//            MyIList<IValue> out = new MyList<>();
//            MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
//
//            PrgState prgState = new PrgState(exeStack, symTable, out, selectedStmt, fileTable);
//
//            String logFilePath = "log" + choice2 + ".txt";
//            IRepo repo = new Repository(logFilePath);
//            repo.addPrgState(prgState);
//
//            this.controller = new Controller(repo);
//
//            System.out.println("Program " + choice2 + " selected. Log file: " + logFilePath);
//            System.out.println("Initial state: ");
//            System.out.println(this.controller.getCrtProgram().toString());
//
//        } catch (NumberFormatException e) {
//            System.err.println("Invalid choice.");
//        }
//    }
//
//    private void runProgram() throws MyException, RepoException, IOException {
//        if (controller == null) {
//            System.err.println("Error: No program selected. Choose option 1 first.");
//            return;
//        }
//
//        System.out.println("Executing program:...");
//
//        PrgState programState = controller.getCrtProgram();
//
//        controller.allStep();
//
//        System.out.println("Execution finished.");
//
//        System.out.println("\n--- Program output ---");
//        System.out.println(programState.getOut().toString());
//        System.out.println("-----------------------------\n");
//
//    }
//
//    private void toggleDisplayFlag() {
//        if (controller == null) {
//            System.err.println("Error. No program selected. Choose option 1 first.");
//        } else {
//            boolean currentFlag = controller.getDisplayFlag();
//            controller.setDisplayFlag(!currentFlag);
//            System.out.println("Display flag set to: " + !currentFlag);
//        }
//    }
//}
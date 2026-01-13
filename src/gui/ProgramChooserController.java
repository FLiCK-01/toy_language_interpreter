package gui;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.PrgState;
import model.adt.MyDictionary;
import model.adt.MyHeap;
import model.adt.MyList;
import model.adt.MyStack;
import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.values.IntValue;
import repository.Repository;
import model.values.IValue;
import model.values.StringValue;
import java.io.BufferedReader;

import java.util.ArrayList;
import java.util.List;

public class ProgramChooserController {
    @FXML
    private ListView<ExampleWrapper> programsListView;
    private List<ExampleWrapper> allExamples = new ArrayList<>();
    @FXML
    public void initialize() {
        IStmt ex1 = new CompStmt(
                new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("a", new ValueExpression(new IntValue(20))),
                        new CompStmt(
                                new ForkStmt(
                                        new CompStmt(
                                                new WriteHeapStmt("a", new ValueExpression(new IntValue(100))),
                                                new PrintStmt(new ReadHeapExp(new VariableExpression("a")))
                                        )
                                ),
                                new CompStmt(
                                        new ForkStmt(
                                                new CompStmt(
                                                        new WriteHeapStmt("a", new ValueExpression(new IntValue(200))),
                                                        new PrintStmt(new ReadHeapExp(new VariableExpression("a")))
                                                )
                                        ),
                                        new PrintStmt(new ValueExpression(new IntValue(300)))
                                )
                        )
                )
        );
        allExamples.add(new ExampleWrapper(ex1, "1. Simple Print: int v=2; print(v)"));

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExpression(new IntValue(1))),
                        new CompStmt(
                                new VarDeclStmt("num", new IntType()),
                                new CompStmt(
                                        new AssignStmt("num", new ValueExpression(new IntValue(5))),
                                        new CompStmt(
                                                new WhileStmt(
                                                        new RelationalExpression(new VariableExpression("num"), new ValueExpression(new IntValue(0)), RelationalOperator.GREATER_THAN),
                                                        new CompStmt(
                                                                new AssignStmt("v", new ArithmeticalExpression(new VariableExpression("v"), new VariableExpression("num"), ArithmeticalOperators.MULTIPLY)),
                                                                new AssignStmt("num", new ArithmeticalExpression(new VariableExpression("num"), new ValueExpression(new IntValue(1)), ArithmeticalOperators.SUBTRACT))
                                                        )
                                                ),
                                                new PrintStmt(new VariableExpression("v"))
                                        )
                                )
                        )
                )
        );
        allExamples.add(new ExampleWrapper(ex2, "2. Arithmetic Operations"));

        IStmt ex3 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExpression(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VariableExpression("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ValueExpression(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );
        allExamples.add(new ExampleWrapper(ex3, "3. Heap Alloc & Reading"));

        IStmt ex4 = new CompStmt(
                new VarDeclStmt("counter", new IntType()),
                new CompStmt(
                        new ForkStmt(
                                new CompStmt(
                                        new ForkStmt(
                                                new CompStmt(
                                                        new AssignStmt("counter", new ValueExpression(new IntValue(100))),
                                                        new PrintStmt(new VariableExpression("counter"))
                                                )
                                        ),
                                        new CompStmt(
                                                new AssignStmt("counter", new ValueExpression(new IntValue(50))),
                                                new PrintStmt(new VariableExpression("counter"))
                                        )
                                )
                        ),
                        new PrintStmt(new ValueExpression(new IntValue(0)))
                )
        );
        allExamples.add(new ExampleWrapper(ex4, "4. Nested Forks: Child & Grandchild Threads"));

        IStmt ex5 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExpression(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExpression(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new WriteHeapStmt("a", new ValueExpression(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExpression(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VariableExpression("v")),
                                                                                new PrintStmt(new ReadHeapExp(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VariableExpression("v")),
                                                        new PrintStmt(new ReadHeapExp(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        allExamples.add(new ExampleWrapper(ex5, "5. Complex: Forks, Heap Write & Shared Memory"));

        IStmt ex6 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenRFile(new VariableExpression("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VariableExpression("varc")),
                                                        new CompStmt(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        allExamples.add(new ExampleWrapper(ex6, "6. File I/O: Open, Read, Print, Close"));

        programsListView.setItems(FXCollections.observableArrayList(allExamples));
    }

    @FXML
    public void displayProgram(ActionEvent actionEvent) {
        ExampleWrapper selectedExample = programsListView.getSelectionModel().getSelectedItem();

        if (selectedExample == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a program!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        IStmt selectedStmt = selectedExample.getStatement();

        try {
            // Run Type Check
            selectedStmt.typeCheck(new MyDictionary<String, IType>());

            // Create PrgState
            PrgState prgState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selectedStmt, new MyDictionary<>(), new MyHeap());
            Repository repo = new Repository("log.txt");
            repo.addPrgState(prgState);
            Controller controller = new Controller(repo);

            // Open Executor
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProgramExecutor.fxml"));
            Parent root = loader.load();

            ProgramExecutorController executorController = loader.getController();
            executorController.setController(controller);

            Stage stage = (Stage) programsListView.getScene().getWindow();
            stage.setTitle("Program Executor");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
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
import model.adt.*;
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
                                new ForStmt("v",
                                        new ValueExpression(new IntValue(0)),
                                        new ValueExpression(new IntValue(3)),
                                        new ArithmeticalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), ArithmeticalOperators.ADD),
                                        new ForkStmt(
                                                new CompStmt(
                                                        new PrintStmt(new VariableExpression("v")),
                                                        new AssignStmt("v", new ArithmeticalExpression(new VariableExpression("v"), new ReadHeapExp(new VariableExpression("a")), ArithmeticalOperators.MULTIPLY))
                                                )
                                        )
                                ),
                                new PrintStmt(new ReadHeapExp(new VariableExpression("a")))
                        )
                )
        );
        allExamples.add(new ExampleWrapper(ex1, "1. For Loop: {0,1,2,20}"));

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("x", new IntType()),
                                new CompStmt(
                                        new VarDeclStmt("q", new IntType()),
                                        new CompStmt(
                                                new NewStmt("v1", new ValueExpression(new IntValue(20))),
                                                new CompStmt(
                                                        new NewStmt("v2", new ValueExpression(new IntValue(30))),
                                                        new CompStmt(
                                                                new NewLockStmt("x"),
                                                                new CompStmt(
                                                                        new ForkStmt(
                                                                                new CompStmt(
                                                                                        new ForkStmt(
                                                                                                new CompStmt(
                                                                                                        new LockStmt("x"),
                                                                                                        new CompStmt(
                                                                                                                new WriteHeapStmt("v1", new ArithmeticalExpression( new ReadHeapExp(new VariableExpression("v1")), new ValueExpression(new IntValue(1)), ArithmeticalOperators.SUBTRACT)),
                                                                                                                new UnlockStmt("x")
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStmt(
                                                                                                new LockStmt("x"),
                                                                                                new CompStmt(
                                                                                                        new WriteHeapStmt("v1", new ArithmeticalExpression(new ReadHeapExp(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), ArithmeticalOperators.MULTIPLY)),
                                                                                                        new UnlockStmt("x")
                                                                                                )
                                                                                        )
                                                                                )
                                                                        ),
                                                                        new CompStmt(
                                                                                new NewLockStmt("q"),
                                                                                new CompStmt(
                                                                                        new ForkStmt(
                                                                                                new CompStmt(
                                                                                                        new ForkStmt(
                                                                                                                new CompStmt(
                                                                                                                        new LockStmt("q"),
                                                                                                                        new CompStmt(
                                                                                                                                new WriteHeapStmt("v2", new ArithmeticalExpression(new ReadHeapExp(new VariableExpression("v2")), new ValueExpression(new IntValue(5)), ArithmeticalOperators.ADD)),
                                                                                                                                new UnlockStmt("q")
                                                                                                                        )
                                                                                                                )
                                                                                                        ),
                                                                                                        new CompStmt(
                                                                                                                new LockStmt("q"),
                                                                                                                new CompStmt(
                                                                                                                        new WriteHeapStmt("v2", new ArithmeticalExpression(new ReadHeapExp(new VariableExpression("v2")), new ValueExpression(new IntValue(10)), ArithmeticalOperators.MULTIPLY)),
                                                                                                                        new UnlockStmt("q")
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStmt(
                                                                                                new NopStmt(),
                                                                                                new CompStmt(
                                                                                                        new NopStmt(),
                                                                                                        new CompStmt(
                                                                                                                new NopStmt(),
                                                                                                                new CompStmt(
                                                                                                                        new NopStmt(),
                                                                                                                        new CompStmt(
                                                                                                                                new LockStmt("x"),
                                                                                                                                new CompStmt(
                                                                                                                                        new PrintStmt(new ReadHeapExp(new VariableExpression("v1"))),
                                                                                                                                        new CompStmt(
                                                                                                                                                new UnlockStmt("x"),
                                                                                                                                                new CompStmt(
                                                                                                                                                        new LockStmt("q"),
                                                                                                                                                        new CompStmt(
                                                                                                                                                                new PrintStmt(new ReadHeapExp(new VariableExpression("v2"))),
                                                                                                                                                                new UnlockStmt("q")
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        allExamples.add(new ExampleWrapper(ex2, "2. Lock Mechanism: {190/199, 350/305}"));

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
            PrgState prgState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selectedStmt, new MyDictionary<>(), new MyHeap(), new MyLockTable());
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
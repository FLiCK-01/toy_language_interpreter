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
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new VarDeclStmt("c", new IntType()),
                                new CompStmt(
                                        new AssignStmt("a", new ValueExpression(new IntValue(1))),
                                        new CompStmt(
                                                new AssignStmt("b", new ValueExpression(new IntValue(2))),
                                                new CompStmt(
                                                        new AssignStmt("c", new ValueExpression(new IntValue(5))),
                                                        new CompStmt(
                                                                new SwitchStmt(
                                                                        new ArithmeticalExpression(new VariableExpression("a"), new ValueExpression(new IntValue(10)), ArithmeticalOperators.MULTIPLY), // switch(a*10)
                                                                        new ArithmeticalExpression(new VariableExpression("b"), new VariableExpression("c"), ArithmeticalOperators.MULTIPLY),           // case b*c
                                                                        new CompStmt(new PrintStmt(new VariableExpression("a")), new PrintStmt(new VariableExpression("b"))), // stmt1
                                                                        new ValueExpression(new IntValue(10)),                                                        // case 10
                                                                        new CompStmt(new PrintStmt(new ValueExpression(new IntValue(100))), new PrintStmt(new ValueExpression(new IntValue(200)))), // stmt2
                                                                        new PrintStmt(new ValueExpression(new IntValue(300)))                                         // default
                                                                ),
                                                                new PrintStmt(new ValueExpression(new IntValue(300)))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        allExamples.add(new ExampleWrapper(ex1, "13. Switch Statement: {1, 2, 300}"));

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("cnt", new IntType()),
                        new CompStmt(
                                new NewStmt("v1", new ValueExpression(new IntValue(1))),
                                new CompStmt(
                                        new CreateSemaphoreStmt("cnt", new ReadHeapExp(new VariableExpression("v1"))), // Semaphore capacity = 1
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new AcquireStmt("cnt"),
                                                                new CompStmt(
                                                                        new WriteHeapStmt("v1", new ArithmeticalExpression(new ReadHeapExp(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), ArithmeticalOperators.MULTIPLY)),
                                                                        new CompStmt(
                                                                                new PrintStmt(new ReadHeapExp(new VariableExpression("v1"))),
                                                                                new ReleaseStmt("cnt")
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new AcquireStmt("cnt"),
                                                                new CompStmt(
                                                                        new WriteHeapStmt("v1", new ArithmeticalExpression(new ReadHeapExp(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), ArithmeticalOperators.MULTIPLY)),
                                                                        new CompStmt(
                                                                                new WriteHeapStmt("v1", new ArithmeticalExpression(new ReadHeapExp(new VariableExpression("v1")), new ValueExpression(new IntValue(2)), ArithmeticalOperators.MULTIPLY)),
                                                                                new CompStmt(
                                                                                        new PrintStmt(new ReadHeapExp(new VariableExpression("v1"))),
                                                                                        new ReleaseStmt("cnt")
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
        allExamples.add(new ExampleWrapper(ex2, "2. Semaphore: {10, 200} or {20, 200}"));

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
            PrgState prgState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selectedStmt, new MyDictionary<>(), new MyHeap(), new MySemaphoreTable());
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
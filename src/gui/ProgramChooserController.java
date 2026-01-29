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
                        new VarDeclStmt("b", new RefType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("v", new IntType()),
                                new CompStmt(
                                        new NewStmt("a", new ValueExpression(new IntValue(0))),
                                        new CompStmt(
                                                new NewStmt("b", new ValueExpression(new IntValue(0))),
                                                new CompStmt(
                                                        new WriteHeapStmt("a", new ValueExpression(new IntValue(1))),
                                                        new CompStmt(
                                                                new WriteHeapStmt("b", new ValueExpression(new IntValue(2))),
                                                                new CompStmt(
                                                                        new ConditionalAssignmentStmt(
                                                                                "v",
                                                                                new RelationalExpression(new ReadHeapExp(new VariableExpression("a")), new ReadHeapExp(new VariableExpression("b")), RelationalOperator.LESS_THAN),
                                                                                new ValueExpression(new IntValue(100)),
                                                                                new ValueExpression(new IntValue(200))
                                                                        ),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VariableExpression("v")),
                                                                                new CompStmt(
                                                                                        new ConditionalAssignmentStmt(
                                                                                                "v",
                                                                                                new RelationalExpression(new ArithmeticalExpression(new ReadHeapExp(new VariableExpression("b")), new ValueExpression(new IntValue(2)), ArithmeticalOperators.SUBTRACT),
                                                                                                        new ReadHeapExp(new VariableExpression("a")), RelationalOperator.GREATER_THAN
                                                                                                ),
                                                                                                new ValueExpression(new IntValue(100)),
                                                                                                new ValueExpression(new IntValue(200))
                                                                                        ),
                                                                                        new PrintStmt(new VariableExpression("v"))
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
        allExamples.add(new ExampleWrapper(ex1, "1. Cond Assignment (v = cond ? 100 : 200)"));

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("v3", new RefType(new IntType())),
                                new CompStmt(
                                        new VarDeclStmt("cnt", new IntType()),
                                        new CompStmt(
                                                new NewStmt("v1", new ValueExpression(new IntValue(2))),
                                                new CompStmt(
                                                        new NewStmt("v2", new ValueExpression(new IntValue(3))),
                                                        new CompStmt(
                                                                new NewStmt("v3", new ValueExpression(new IntValue(4))),
                                                                new CompStmt(
                                                                        new NewLatchStmt("cnt", new ReadHeapExp(new VariableExpression("v2"))), // Latch count = 3
                                                                        new CompStmt(
                                                                                new ForkStmt(
                                                                                        new CompStmt(
                                                                                                new WriteHeapStmt("v1", new ArithmeticalExpression(new ReadHeapExp(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), ArithmeticalOperators.MULTIPLY)),
                                                                                                new CompStmt(
                                                                                                        new PrintStmt(new ReadHeapExp(new VariableExpression("v1"))),
                                                                                                        new CountDownStmt("cnt")
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompStmt(
                                                                                        new ForkStmt(
                                                                                                new CompStmt(
                                                                                                        new WriteHeapStmt("v2", new ArithmeticalExpression(new ReadHeapExp(new VariableExpression("v2")), new ValueExpression(new IntValue(10)), ArithmeticalOperators.MULTIPLY)),
                                                                                                        new CompStmt(
                                                                                                                new PrintStmt(new ReadHeapExp(new VariableExpression("v2"))),
                                                                                                                new CountDownStmt("cnt")
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStmt(
                                                                                                new ForkStmt(
                                                                                                        new CompStmt(
                                                                                                                new WriteHeapStmt("v3", new ArithmeticalExpression(new ReadHeapExp(new VariableExpression("v3")), new ValueExpression(new IntValue(10)), ArithmeticalOperators.MULTIPLY)),
                                                                                                                new CompStmt(
                                                                                                                        new PrintStmt(new ReadHeapExp(new VariableExpression("v3"))),
                                                                                                                        new CountDownStmt("cnt")
                                                                                                                )
                                                                                                        )
                                                                                                ),
                                                                                                new CompStmt(
                                                                                                        new AwaitStmt("cnt"),
                                                                                                        new CompStmt(
                                                                                                                new PrintStmt(new ValueExpression(new IntValue(100))),
                                                                                                                new CompStmt(
                                                                                                                        new CountDownStmt("cnt"),
                                                                                                                        new PrintStmt(new ValueExpression(new IntValue(100)))
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
        allExamples.add(new ExampleWrapper(ex2, "2. CountDownLatch: {20,30,40,100,100}"));

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
            PrgState prgState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selectedStmt, new MyDictionary<>(), new MyHeap(), new MyLatchTable());
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
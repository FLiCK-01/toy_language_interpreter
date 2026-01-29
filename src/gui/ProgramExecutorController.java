package gui;

import controller.IController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.PrgState;
import model.adt.MyISemaphoreTable;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgramExecutorController {
    private IController controller;

    @FXML
    private TextField numberOfPrgStatesTextField;
    @FXML
    private TableView<Map.Entry<Integer, IValue>> heapTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, Integer> addressColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, String> valueColumn;
    @FXML
    private ListView<String> outListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private ListView<Integer> prgStateIdentifiersListView;
    @FXML
    private TableView<Map.Entry<String, IValue>> symbolTableView;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> variableNameColumn;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> variableValueColumn;
    @FXML
    private ListView<String> executionStackListView;
    @FXML
    private TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> semaphoreTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> semaphoreIndexColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> semaphoreValueColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> semaphoreListColumn;
    @FXML
    private Button runOneStepButton;

    public void setController(IController controller) {
        this.controller = controller;
        populate();
    }

    @FXML
    public void initialize() {
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));

        variableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        variableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));

        semaphoreIndexColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey().toString()));
        semaphoreValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().getKey().toString()));
        semaphoreListColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().getValue().toString()));

        prgStateIdentifiersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void changeProgramState(MouseEvent event) {
        populateExecutionStack();
        populateSymbolTable();
    }

    @FXML
    public void runOneStep(javafx.event.ActionEvent actionEvent) {
        if(controller == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No program selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            List<PrgState> programStates = controller.getRepo().getPrgList();

            if (programStates.size() > 0) {
                controller.oneStepForAllPrg(programStates);
                populate();

                programStates = controller.removeCompletedPrg(controller.getRepo().getPrgList());

                controller.getRepo().setPrgList(programStates);

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Program finished", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void populate() {
        populateHeap();
        populateProgramStateIdentifiers();
        populateFileTable();
        populateOut();
        populateSymbolTable();
        populateExecutionStack();
        populateSemaphoreTable();
    }

    private void populateHeap() {
        if (controller.getRepo().getPrgList().size() > 0) {
            Map<Integer, IValue> heapContent = controller.getRepo().getPrgList().get(0).getHeap().getContent();
            List<Map.Entry<Integer, IValue>> heapTableList = new ArrayList<>(heapContent.entrySet());
            heapTableView.setItems(FXCollections.observableList(heapTableList));
            heapTableView.refresh();
        }
    }

    private void populateProgramStateIdentifiers() {
        List<PrgState> programStates = controller.getRepo().getPrgList();
        List<Integer> idList = programStates.stream().map(PrgState::getId).collect(Collectors.toList());
        prgStateIdentifiersListView.setItems(FXCollections.observableList(idList));
        numberOfPrgStatesTextField.setText(String.valueOf(programStates.size()));
    }

    private void populateFileTable() {
        if (controller.getRepo().getPrgList().size() > 0) {
            Map<StringValue, java.io.BufferedReader> fileTable = controller.getRepo().getPrgList().get(0).getFileTable().getContent();
            List<String> files = new ArrayList<>();
            for (StringValue key : fileTable.keySet()) {
                files.add(key.toString());
            }
            fileTableListView.setItems(FXCollections.observableList(files));
        }
    }

    private void populateOut() {
        if (controller.getRepo().getPrgList().size() > 0) {
            // This requires the 'getList()' method in MyIList
            List<String> output = controller.getRepo().getPrgList().get(0).getOut().getList().stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            outListView.setItems(FXCollections.observableList(output));
        }
    }

    private void populateSymbolTable() {
        PrgState currentPrgState = getCurrentProgramState();
        List<Map.Entry<String, IValue>> symbolTableList = new ArrayList<>();

        if (currentPrgState != null) {
            symbolTableList.addAll(currentPrgState.getSymTable().getContent().entrySet());
        }
        symbolTableView.setItems(FXCollections.observableList(symbolTableList));
        symbolTableView.refresh();
    }

    private void populateExecutionStack() {
        PrgState currentPrgState = getCurrentProgramState();
        List<String> exeStackList = new ArrayList<>();

        if (currentPrgState != null) {
            for(IStmt stmt : currentPrgState.getExeStack().getReverse()){
                exeStackList.add(stmt.toString());
            }
        }
        executionStackListView.setItems(FXCollections.observableList(exeStackList));
    }

    private void populateSemaphoreTable() {
        if (controller.getRepo().getPrgList().size() > 0) {
            MyISemaphoreTable semTable = controller.getRepo().getPrgList().get(0).getSemaphoreTable();
            List<Map.Entry<Integer, Pair<Integer, List<Integer>>>> semList = new ArrayList<>(semTable.getContent().entrySet());
            semaphoreTableView.setItems(FXCollections.observableList(semList));
            semaphoreTableView.refresh();
        }
    }

    private PrgState getCurrentProgramState(){
        if(controller.getRepo().getPrgList().size() == 0) return null;

        Integer selectedId = prgStateIdentifiersListView.getSelectionModel().getSelectedItem();
        int currentId = (selectedId != null) ? selectedId : controller.getRepo().getPrgList().get(0).getId();

        return controller.getRepo().getPrgList().stream()
                .filter(p -> p.getId() == currentId)
                .findAny()
                .orElse(null);
    }

    @FXML
    public void backToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProgramChooser.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 400);

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setTitle("Program Chooser");
        stage.setScene(scene);
        stage.show();
    }
}
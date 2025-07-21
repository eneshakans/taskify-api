package rocks.halhadus.taskify;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainView extends BorderPane {
    private TaskManager taskManager = new TaskManager();
    private ObservableList<Task> observableTaskList = FXCollections.observableArrayList();
    private TableView<Task> taskTable = new TaskTable(observableTaskList);

    public MainView() {
        FileOps.loadJSON(taskManager);
        observableTaskList.addAll(taskManager.getTasks());
        setupTopPanel();
        setCenter(taskTable);
        setPadding(new Insets(10));
    }

    private void setupTopPanel() {
        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll("All", "Completed", "Not Completed");
        filterComboBox.setValue("All");
        filterComboBox.setOnAction(e -> TaskifyMethods.filterTasks(taskManager, filterComboBox, observableTaskList));
        Button addButton = new Button("Add Task");
        Button removeButton = new Button("Remove Task");
        Button toggleButton = new Button("Toggle Status");
        Button clearButton = new Button("Clear All");
        addButton.setOnAction(e -> TaskifyMethods.showAddTaskDialog(taskManager, observableTaskList));
        removeButton.setOnAction(e -> TaskifyMethods.removeSelectedTask(taskManager, taskTable, observableTaskList));
        toggleButton.setOnAction(e -> TaskifyMethods.toggleSelectedTaskStatus(taskManager, taskTable));
        clearButton.setOnAction(e -> TaskifyMethods.clearAllTasks(taskManager, observableTaskList));
        HBox buttonBox = new HBox(10, filterComboBox, addButton, removeButton, toggleButton, clearButton);
        buttonBox.setPadding(new Insets(0, 0, 10, 0));
        setTop(buttonBox);
    }
}
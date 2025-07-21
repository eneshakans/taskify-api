package rocks.halhadus.taskify;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;

public class TaskifyMethods {

    public static void showAddTaskDialog(TaskManager taskManager, ObservableList<Task> observableList) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add new task");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        TextField titleField = new TextField();
        titleField.setPromptText("Task Title");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Task Description");
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionArea, 1, 1);
        GridPane.setHgrow(titleField, Priority.ALWAYS);
        GridPane.setHgrow(descriptionArea, Priority.ALWAYS);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(titleField.getText(), descriptionArea.getText());
            }
            return null;
        });
        dialog.showAndWait().ifPresent(result -> {
            String title = result.getKey();
            String description = result.getValue();
            if (title == null || title.trim().isEmpty()) title = "Untitled Task";
            if (description == null || description.trim().isEmpty()) description = "No description";
            Task newTask = new Task(title, description);
            taskManager.getTasks().add(newTask);
            observableList.add(newTask);
            FileOps.writeJSON(taskManager);
            showAlert("Successful", "Task added: " + title);
        });
    }

    public static void removeSelectedTask(TaskManager taskManager, TableView<Task> table, ObservableList<Task> observableList) {
        Task selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            taskManager.getTasks().remove(selected);
            observableList.remove(selected);
            FileOps.writeJSON(taskManager);
            showAlert("Successful", "Task removed: " + selected.getTitle());
        } else {
            showAlert("Warning", "Please select a task to remove!");
        }
    }

    public static void toggleSelectedTaskStatus(TaskManager taskManager, TableView<Task> table) {
        Task selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatus(!selected.isStatus());
            table.refresh();
            FileOps.writeJSON(taskManager);
            showAlert("Successful", "Task status toggled: " + selected.getTitle());
        } else {
            showAlert("Warning", "Please select a task to toggle status!");
        }
    }

    public static void clearAllTasks(TaskManager taskManager, ObservableList<Task> observableList) {
        taskManager.clearTaskList();
        observableList.clear();
        FileOps.writeJSON(taskManager);
        showAlert("Successful", "Tasks are cleared!");
    }

    public static void filterTasks(TaskManager taskManager, ComboBox<String> comboBox, ObservableList<Task> observableList) {
        String filter = comboBox.getValue();
        observableList.clear();
        switch (filter) {
            case "Completed":
                for (Task task : taskManager.getTasks()) {
                    if (task.isStatus()) observableList.add(task);
                }
                break;
            case "Not Completed":
                for (Task task : taskManager.getTasks()) {
                    if (!task.isStatus()) observableList.add(task);
                }
                break;
            default:
                observableList.addAll(taskManager.getTasks());
        }
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
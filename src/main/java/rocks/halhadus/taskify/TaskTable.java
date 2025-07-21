package rocks.halhadus.taskify;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TaskTable extends TableView<Task> {
    public TaskTable(ObservableList<Task> taskList) {
        setItems(taskList);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN); // check this later
        createColumns();
    }

    private void createColumns() {
        TableColumn<Task, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Task, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<Task, String> dateCol = new TableColumn<>("Creation Date");
        dateCol.setCellValueFactory(cellData -> {Double sec = cellData.getValue().getCreationDate(); return new javafx.beans.property.SimpleStringProperty(DateOps.formatDate(DateOps.secToDate(sec)));});
        TableColumn<Task, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> {boolean status = cellData.getValue().isStatus(); return new javafx.beans.property.SimpleStringProperty(status ? "Completed" : "Not Completed");});
        getColumns().addAll(titleCol, descCol, dateCol, statusCol);
    }
}
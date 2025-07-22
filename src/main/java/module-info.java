module rocks.halhadus.taskify {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;


    opens rocks.halhadus.taskify to javafx.fxml, com.google.gson;
    exports rocks.halhadus.taskify;
}
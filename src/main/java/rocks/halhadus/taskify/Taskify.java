package rocks.halhadus.taskify;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Taskify extends Application {
    public void start(Stage primaryStage) {
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView, 800, 600);
        primaryStage.setTitle("Taskify");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
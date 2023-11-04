package ber.soundboard.berssoundboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;

public class MainApp extends Application {
    static String NAME = "balls"; //TODO remove test case
    public static HashMap<String, Object> GLOBAL_DATA = new HashMap<>();

    @Override
    public void start(Stage stage) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("selectplay.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("mixer2.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("selectplay.css").toExternalForm());
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
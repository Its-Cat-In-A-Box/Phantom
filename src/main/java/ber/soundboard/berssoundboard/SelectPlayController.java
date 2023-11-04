
package ber.soundboard.berssoundboard;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SelectPlayController implements Initializable{
    private Stage stage;
    private Scene scene;


    @FXML
    private Button firstrecent;

    @FXML
    private Button secondrecent;

    @FXML
    private Button thirdrecent;




    @FXML
    private Button closeButton;
    @FXML
    private Pane addNewButton;
    @FXML
    private Pane openButton;


    @FXML
    private Label qotd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File playDir = new File("data");
        if (!playDir.exists()){
            playDir.mkdir();
        }
        File[] directories = playDir.listFiles((dir, child) -> new File(dir, child).isDirectory());
        if (directories != null || directories.length != 0){
            Arrays.sort(directories, Comparator.comparingLong(File::lastModified));
            switch (directories.length){
                case 0:
                    break;
                case 1:
                    firstrecent.setText(directories[0].getName());
                    secondrecent.setText("");
                    thirdrecent.setText("");
                    break;
                case 2:
                    firstrecent.setText(directories[0].getName());
                    secondrecent.setText(directories[1].getName());
                    thirdrecent.setText("");
                    break;
                default:
                    firstrecent.setText(directories[0].getName());
                    secondrecent.setText(directories[1].getName());
                    thirdrecent.setText(directories[2].getName());
                    break;
            }
        } else {
            firstrecent.setText("");
            secondrecent.setText("");
            thirdrecent.setText("");
        }


        Random random = new Random();
        String[] randomQotd = {
                "I've been there before, slaving away make sound effects",
                "What should be the quote",
                "I feel your pain",
                "Didn't expect to see me here huh",
                "From the best EDC chair",
                "Exclusively tailored for EDC",
                "Imagine if it was called English Theatre Club",
                "Update me on this year's play will ya?",
                "Courtesy of @lausir_ict, unless i changed the @",
                "Courtesy of the best edc chair",
                "According to fallacy of composition, this app is not trash",
                "According to appeal to probability, this app won't crash",
                "According to appeal to probability, this app doesn't have bugs",
                "Quiz time: Who made this app?",
        };
        qotd.setText(randomQotd[random.nextInt(0, randomQotd.length)]);

    }

    @FXML
    void onMouseClickedExitButton(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onMouseEnteredExitButton(MouseEvent event){
        closeButton.setStyle("-fx-background-color: red");
    }

    @FXML
    void onMouseExitedExitButton(MouseEvent event){
        closeButton.setStyle("-fx-background-color: null");
    }


    @FXML
    void onAddNewButtonClicked(MouseEvent event) {

    }

    @FXML
    void onAddNewButtonEntered(MouseEvent event) {
        addNewButton.setStyle("-fx-background-color: Gray");
    }

    @FXML
    void onAddNewButtonExited(MouseEvent event) {
        addNewButton.setStyle("-fx-background-color: null");
    }
    @FXML
    void onOpenButtonClicked(MouseEvent event) throws IOException {
        loadMixer(event, firstrecent.getText());
    }

    @FXML
    void onOpenButtonEntered(MouseEvent event) {
        openButton.setStyle("-fx-background-color: Gray");
    }

    @FXML
    void onOpenButtonExited(MouseEvent event) {
        openButton.setStyle("-fx-background-color: null");
    }

    @FXML
    void onFirstRecentClicked(MouseEvent event) throws IOException {
        loadMixer(event, firstrecent.getText());
    }


    @FXML
    void onSecondRecentClicked(MouseEvent event) throws IOException {
        loadMixer(event, secondrecent.getText());
    }

    @FXML
    void onThirdRecentClicked(MouseEvent event) throws IOException {
        loadMixer(event, thirdrecent.getText());
    }

    private void loadMixer(MouseEvent event, String name) throws IOException {
        if (firstrecent.getText() != null && !Objects.equals(firstrecent.getText().trim(), "")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mixer.fxml"));
            Parent root = (Parent) loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Rectangle2D bounds = Screen.getScreens().get(0).getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());

            Platform.runLater(() -> {
                stage.setFullScreen(false);
                stage.setFullScreen(true);

                scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            });

        }
    }

}

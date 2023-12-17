
package ber.soundboard.berssoundboard;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import org.jetbrains.annotations.NotNull;


import java.io.File;
import java.io.FileReader;
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
            Arrays.sort(directories, Comparator.comparingLong(File::lastModified).reversed());
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
                "I've been there before, slaving away making sound effects",
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
        qotd.setText(randomQotd[random.nextInt(randomQotd.length)]);

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
    void onAddNewButtonClicked(MouseEvent event) throws IOException {
        addNewScene();
    }

    private void addNewScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newplaypopup.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setFullScreen(false);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
        ((Stage) closeButton.getScene().getWindow()).close();

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
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(System.getProperty("user.dir"), "data"));
        File dir = dc.showDialog(addNewButton.getScene().getWindow());

        File jsonFile = new File(dir, dir.getName() + ".json");
        if (jsonFile.exists()){
            Gson gson = new Gson();
            JsonReader reader;
            reader = new JsonReader(new FileReader(jsonFile));

            MixerJsonObject jsonObj = gson.fromJson(reader, MixerJsonObject.class);
            File[] files = dir.listFiles();
            List<String> fileNames = new ArrayList<>();
            for (File f : files){
                fileNames.add(f.getName());
            }

            boolean corruption = false;
            String errorFile = null;
            for (MixerJsonObject.SoundFiles sf : jsonObj.listOfSounds){
                if (!fileNames.contains(sf.soundFile)){
                    corruption = true;
                    errorFile = sf.soundFile;
                    break;
                }

            }
            ButtonType bt = new ButtonType("Open File Location", ButtonBar.ButtonData.APPLY);

            if (corruption) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "File " + errorFile + " is not found, play may be corrupted!",
                        ButtonType.CLOSE, bt);

                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE){
                    return;
                } else {
                    Runtime.getRuntime().exec("explorer " + dir);
                }
            }
        }

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
    void onFirstRecentClicked(MouseEvent event) throws Exception {
        loadMixer(event, firstrecent.getText());
    }


    @FXML
    void onSecondRecentClicked(MouseEvent event) throws Exception {
        loadMixer(event, secondrecent.getText());
    }

    @FXML
    void onThirdRecentClicked(MouseEvent event) throws Exception {
        loadMixer(event, thirdrecent.getText());
    }

    private void loadMixer(MouseEvent event, String name) throws Exception {
        if (!Objects.equals(name, "")){
            MainApp.flushDIN();
            MainApp.NAME = name;
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("splash.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            ((Stage)thirdrecent.getScene().getWindow()).close();
        }
    }



}

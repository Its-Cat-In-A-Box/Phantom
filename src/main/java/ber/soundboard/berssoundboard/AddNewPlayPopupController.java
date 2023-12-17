package ber.soundboard.berssoundboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddNewPlayPopupController implements Initializable {
    @FXML
    private TextField playName;
    @FXML
    private Label errorLabel;
    @FXML
    private Button confirmBtn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] dir = new File(System.getProperty("user.dir") + "\\data\\").list((dir1, name) -> new File(dir1, name).isDirectory());

        System.out.println(Arrays.toString(dir));
        playName.textProperty().addListener((observable, oldValue, newValue) -> {
            List<String> existingDir = List.of(dir);
            if (existingDir.contains(newValue)){
                confirmBtn.setDisable(true);
                errorLabel.setOpacity(1);
                return;
            } else {
                confirmBtn.setDisable(false);
                errorLabel.setOpacity(0);
            }
            if (newValue.trim().isEmpty()){
                confirmBtn.setDisable(true);
            } else {
                confirmBtn.setDisable(false);
            }
        });
    }

    public void newPlayConfirmBtnClicked(ActionEvent actionEvent) throws IOException {
        File newDir = new File(System.getProperty("user.dir") + "\\data\\", playName.getText());
        newDir.mkdir();
        File newJson = new File(System.getProperty("user.dir") + "\\data\\", playName.getText() + "\\" + playName.getText() + ".json");
        newJson.createNewFile();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\data\\" +  playName.getText() + "\\" + playName.getText() + ".json");
        gson.toJson(new MixerJsonObject(), writer);
        writer.close();

        MainApp.NAME = playName.getText();
        Parent root = FXMLLoader.load(getClass().getResource("splash.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
        ((Stage) confirmBtn.getScene().getWindow()).close();
    }

    public void cancelBtnClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("selectplay.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        ((Stage)confirmBtn.getScene().getWindow()).close();
    }
}

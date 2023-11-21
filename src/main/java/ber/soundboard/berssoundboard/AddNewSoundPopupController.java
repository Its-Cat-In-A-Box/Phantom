package ber.soundboard.berssoundboard;

import com.almasb.fxgl.entity.action.Action;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddNewSoundPopupController implements Initializable {
    @FXML
    private Button fileSelectButton;
    @FXML
    private TextField soundFilePathText;
    @FXML
    private TextField soundNamePathText;
    @FXML
    private Label errorSoundFile;
    @FXML
    private Label errorSoundName;
    @FXML
    private Button confirmButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(System.getProperty("user.dir") + "\\data\\" + MainApp.NAME + "\\" + MainApp.NAME + ".json"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        MixerJsonObject mixerJsonObject = gson.fromJson(reader, MixerJsonObject.class);
        MixerJsonObject.SoundFiles[] soundFiles = mixerJsonObject.listOfSounds;
        soundFilePathText.textProperty().addListener(((observable, oldValue, newValue) -> {
            File file = new File(newValue);
            if (!file.exists() || file.isDirectory()) {
                errorSoundFile.setOpacity(1);
                confirmButton.setDisable(true);
            } else {
                errorSoundFile.setOpacity(0);
                confirmButton.setDisable(false);
            }

        }));

        soundNamePathText.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (soundFiles.length == 0)
                confirmButton.setDisable(false);
            for (MixerJsonObject.SoundFiles sf : soundFiles){
                if (sf.soundFile.split("\\.")[0].equals(newValue)){
                    errorSoundName.setText("A sound with the same name exists!");
                    errorSoundName.setOpacity(1);
                    confirmButton.setDisable(true);
                    return;
                } else {
                    errorSoundName.setOpacity(0);
                    confirmButton.setDisable(false);
                }
            }
            if (newValue.isEmpty()){
                errorSoundName.setText("Sound name cannot be empty!");
                errorSoundName.setOpacity(1);
                confirmButton.setDisable(true);
                return;
            } else {
                errorSoundName.setOpacity(0);
                confirmButton.setDisable(false);
            }

        }));


    }

    @FXML
    void fileSelectorPopup(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(soundFilePathText.getScene().getWindow());
        if (file != null){
            soundFilePathText.setText(file.getPath());
            soundNamePathText.setText(file.getName().split("\\.")[0]);
        }
    }

    @FXML
    void confirmAction(ActionEvent event) throws IOException {
        MainApp.GLOBAL_DATA.put("HasNewData", "true");
        Gson gson = new Gson();
        JsonReader reader;
        File jsonFile = new File(System.getProperty("user.dir") + "\\data\\" + MainApp.NAME + "\\" + MainApp.NAME + ".json");
        try {
            reader = new JsonReader(new FileReader(jsonFile.getPath()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        MixerJsonObject mixerJsonObject = gson.fromJson(reader, MixerJsonObject.class);
        List<MixerJsonObject.SoundFiles> soundFiles = new ArrayList<>(Arrays.asList(mixerJsonObject.listOfSounds));

        Path musicFile = Paths.get(soundFilePathText.getText());
        Path copiedFile = Paths.get(System.getProperty("user.dir"), "data", MainApp.NAME,
                soundNamePathText.getText() + "." + soundFilePathText.getText().split("\\.")[soundFilePathText.getText().split("\\.").length -1]);

        Files.copy(musicFile, copiedFile);

        soundFiles.add(new MixerJsonObject.SoundFiles(soundFiles.size(), soundNamePathText.getText() + "." + soundFilePathText.getText().split("\\.")[soundFilePathText.getText().split("\\.").length -1]));
        mixerJsonObject.listOfSounds = soundFiles.toArray(new MixerJsonObject.SoundFiles[soundFiles.size()]);
        gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        FileWriter writer = new FileWriter(jsonFile);
        gson.toJson(mixerJsonObject, writer);
        writer.close();

        ((Stage) confirmButton.getScene().getWindow()).close();
    }

    @FXML
    void cancelClicked(ActionEvent event){
        MainApp.GLOBAL_DATA.put("HasNewData", false);
        ((Stage) confirmButton.getScene().getWindow()).close();
    }
}

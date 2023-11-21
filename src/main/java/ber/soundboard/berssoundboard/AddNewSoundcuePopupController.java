package ber.soundboard.berssoundboard;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddNewSoundcuePopupController implements Initializable {
    @FXML
    private TextField soundcueName;
    @FXML
    private Label nameError;

    @FXML
    private Button confirmBtn, cancelBtn;
    private MixerJsonObject mixerJsonObject;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(System.getProperty("user.dir") + "\\data\\" + MainApp.NAME + "\\" + MainApp.NAME + ".json"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        mixerJsonObject = gson.fromJson(reader, MixerJsonObject.class);
        String currentScene = (String) MainApp.GLOBAL_DATA.get("scene");
        System.out.println(currentScene);
        MixerJsonObject.PlayScene[] sceneList = mixerJsonObject.scenes;
        MixerJsonObject.PlayScene scene = null;
        for (MixerJsonObject.PlayScene ps : sceneList){
            System.out.println(ps.name);
            if (ps.name.equals(currentScene)){
                scene = ps;
            }
        }

        MixerJsonObject.PlayScene.SoundConfiguration[] soundConfig = scene.soundConfiguration;
        List<String> soundConfName = new ArrayList<>();
        List<Integer> soundConfNo = new ArrayList<>();
        if (soundConfig != null){
            for (MixerJsonObject.PlayScene.SoundConfiguration sf :soundConfig){
                soundConfName.add(sf.cueName);
                soundConfNo.add(sf.cueId);
            }
        }


        soundcueName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 5){
                soundcueName.setText(oldValue);
            }
            if (soundConfName.contains(newValue)){
                nameError.setOpacity(1);
                confirmBtn.setDisable(true);
            } else if (newValue.isEmpty()) {
                nameError.setOpacity(0);
                confirmBtn.setDisable(true);
            } else {
                nameError.setOpacity(0);
                confirmBtn.setDisable(false);
            }
        });


        MixerJsonObject.PlayScene finalScene = scene;
        confirmBtn.setOnAction(event -> {
            MainApp.GLOBAL_DATA.put("newcuename", soundcueName.getText());
            MainApp.GLOBAL_DATA.put("HasNewData", true);
            ((Stage) confirmBtn.getScene().getWindow()).close();
        });
        cancelBtn.setOnAction(event -> {
            MainApp.GLOBAL_DATA.put("HasNewData", true);
            ((Stage) confirmBtn.getScene().getWindow()).close();
        });
    }

}

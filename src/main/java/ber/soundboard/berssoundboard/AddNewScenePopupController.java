package ber.soundboard.berssoundboard;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddNewScenePopupController implements Initializable {
    @FXML
    private Button cancelButton;

    @FXML
    private Button newSceneConfirmBtn;

    @FXML
    private TextField sceneName;

    @FXML
    private TextField sceneNumber;

    @FXML
    private Label sceneNameWarning;

    @FXML
    private Label sceneNumberWarning;

    private boolean hasSceneName = false;
    private boolean hasSceneNumber = false;


    @FXML
    void cancelBtnClicked(ActionEvent event) {
        MainApp.GLOBAL_DATA.put("HasNewData", "false");
        ((Stage) sceneNumber.getScene().getWindow()).close();
    }

    @FXML
    void newSceneConfirmBtnClicked(ActionEvent event) {
        MainApp.GLOBAL_DATA.put("HasNewData", "true");
        MainApp.GLOBAL_DATA.put("NewSceneName", sceneName.getText());
        MainApp.GLOBAL_DATA.put("NewSceneNumber", sceneNumber.getText());
        ((Stage) sceneNumber.getScene().getWindow()).close();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Gson gson = new Gson();
        File mixerConfigFile = new File(System.getProperty("user.dir") + "\\data\\" + MainApp.NAME + "\\" + MainApp.NAME + ".json");
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(mixerConfigFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        MixerJsonObject jsonObject = gson.fromJson(reader, MixerJsonObject.class);
        List<String> sceneNames = new ArrayList<>();
        for (MixerJsonObject.PlayScene playScene : jsonObject.scenes){
            sceneNames.add(playScene.name);
        }
        List<Integer> sceneNumbers = new ArrayList<>();
        for (MixerJsonObject.PlayScene playScene : jsonObject.scenes){
            sceneNumbers.add(playScene.sceneIdx);
        }


        sceneName.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\S*")){
                sceneName.setText(newValue.replaceAll("[^\\S*]", ""));
            } else if(sceneNames.contains(newValue)){
                sceneNameWarning.setOpacity(1);
                newSceneConfirmBtn.setDisable(true);
            } else if (newValue.isEmpty()){
                sceneNameWarning.setOpacity(0);
                hasSceneName = false;
                newSceneConfirmBtn.setDisable(true);
            }
            else {
                hasSceneName = true;
                sceneNameWarning.setOpacity(0);
                if (hasSceneNumber && hasSceneName){
                    newSceneConfirmBtn.setDisable(false);
                }
            }
        }));
        sceneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                sceneNumber.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (newValue.isEmpty()){
                sceneNumberWarning.setOpacity(0);
                hasSceneNumber = false;
                newSceneConfirmBtn.setDisable(true);
            }
            else if (sceneNumbers.contains(Integer.parseInt(newValue))) {
                sceneNumberWarning.setOpacity(1);
                newSceneConfirmBtn.setDisable(true);
            } else {
                hasSceneNumber = true;
                sceneNumberWarning.setOpacity(0);
                if (hasSceneNumber && hasSceneName){
                    newSceneConfirmBtn.setDisable(false);
                }
            }
        });
    }
}

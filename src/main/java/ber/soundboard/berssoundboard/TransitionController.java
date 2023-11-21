package ber.soundboard.berssoundboard;

import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.entity.action.Action;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransitionController implements Initializable {
    MixerJsonObject.PlayScene currentScene;
    MixerJsonObject mixerJsonObject;
    int curCueOrder;
    int curTransOrder;

    @FXML
    private TextField cueOrder, transOrder, startingVol, transVol, transTime;
    @FXML
    private MenuButton soundcueMenu, soundMenu, transitionMenu, transTypeMenu;
    private boolean isNewCue;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(System.getProperty("user.dir") + "\\data\\" + MainApp.NAME + "\\" + MainApp.NAME +".json"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        mixerJsonObject = gson.fromJson(reader, MixerJsonObject.class);

        currentScene = getScene((String) MainApp.GLOBAL_DATA.get("scene"), mixerJsonObject);

        if (currentScene.soundConfiguration != null){
            for (MixerJsonObject.PlayScene.SoundConfiguration sc : currentScene.soundConfiguration){
                MenuItem mi = new MenuItem(sc.cueName);
                mi.setOnAction(event -> {
                    isNewCue = false;
                    soundcueMenu.setText(sc.cueName);
                    curCueOrder = sc.cueId;
                    cueOrder.setText(String.valueOf(sc.cueId));
                    startingVol.setText(String.valueOf(sc.startVol));
                    for (MixerJsonObject.SoundFiles sf : mixerJsonObject.listOfSounds){
                        if (sf.soundId == sc.soundId){
                            soundMenu.setText(sf.soundFile);
                        }
                    }
                    loadTransition(sc);
                });

                soundcueMenu.getItems().add(mi);
            }
        }

        MenuItem newConf = new MenuItem("+ New Soundcue");
        newConf.setOnAction(event -> {
            try {
                isNewCue = true;
                addNewSoundCue();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        soundcueMenu.getItems().add(newConf);

        if (currentScene.soundConfiguration != null && currentScene.soundConfiguration.length > 0){
            MixerJsonObject.PlayScene.SoundConfiguration[] sc = currentScene.soundConfiguration;
            List<Integer> cues = new ArrayList<>();
            for (MixerJsonObject.PlayScene.SoundConfiguration conf : sc){
                cues.add(conf.cueId);
            }
            cueOrder.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty() &&(cues.contains(Integer.parseInt(newValue))) && Integer.parseInt(newValue) != curCueOrder){
                    cueOrder.setStyle("-fx-background-color: #303030; -fx-text-fill: red");
                } else {
                    cueOrder.setStyle("-fx-background-color: #303030; -fx-text-fill: white");
                }
            });
        }
        cueOrder.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                cueOrder.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 2){
                cueOrder.setText(oldValue);
            }
        });

        startingVol.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                startingVol.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 3){
                startingVol.setText(oldValue);
            }
        });


        if (mixerJsonObject.listOfSounds != null && mixerJsonObject.listOfSounds.length > 0){
            for (MixerJsonObject.SoundFiles sf : mixerJsonObject.listOfSounds){
                MenuItem mi = new MenuItem(sf.soundFile);
                mi.setOnAction(event -> soundMenu.setText(mi.getText()));
                soundMenu.getItems().add(mi);
            }
        } else {
            soundMenu.getItems().add(new MenuItem("You have no sounds!"));
        }
        MenuItem mi = new MenuItem("New transition");
        mi.setOnAction(event -> transitionMenu.setText("New transition"));
        transitionMenu.getItems().add(mi);


        transOrder.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                transOrder.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 2){
                transOrder.setText(oldValue);
            }
        });
        MenuItem fadeinMi = new MenuItem("FADEIN");
        fadeinMi.setOnAction(event -> {
            transTypeMenu.setText("FADEIN");
            transVol.setText("");
            transVol.setDisable(false);
        });
        MenuItem fadeMi = new MenuItem("FADE");
        fadeMi.setOnAction(event -> {
            transTypeMenu.setText("FADE");
            transVol.setText("");
            transVol.setDisable(false);
        });
        MenuItem fadeoutMi = new MenuItem("FADEOUT");
        fadeoutMi.setOnAction(event -> {
            transTypeMenu.setText("FADEOUT");
            transVol.setText("0");
            transVol.setDisable(true);
        });

        transTypeMenu.getItems().addAll(fadeinMi, fadeMi, fadeoutMi);

        transVol.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                transVol.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 3){
                transVol.setText(oldValue);
            }
        });

        transTime.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9.]+")){
                transTime.setText(newValue.replaceAll("[^[0-9.]]", ""));
            }
            if (newValue.length() > 4){
                transTime.setText(oldValue);
            }
            if (newValue.toCharArray()[0] == '.' || newValue.toCharArray()[newValue.length() - 1] == '.'){
                transTime.setText(oldValue);
            }
        });

    }

    private void loadTransition(MixerJsonObject.PlayScene.SoundConfiguration soundConfiguration) {
        if (soundConfiguration.transitions != null && soundConfiguration.transitions.length > 0){
            MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition[] transitions = soundConfiguration.transitions;
            List<MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition> configList =  Stream.of(transitions).sorted((Comparator.comparingInt(o -> o.transitionId))).collect(Collectors.toList());

            for (MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition transition : configList){
                MenuItem mi = new MenuItem(transition.transitionId + ". " + transition.transitionType);
                mi.setOnAction(event -> {
                    curCueOrder = transition.transitionId;
                    transitionMenu.setText(mi.getText());
                    transOrder.setText(String.valueOf(transition.transitionId));
                    transTypeMenu.setText(transition.transitionType);
                    transVol.setText(String.valueOf(transition.newVolume));
                    transTime.setText(String.valueOf(transition.transitionTime / 1000));
                });
                transitionMenu.getItems().add(mi);
            }

        }


    }

    void addNewSoundCue() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newsoundcue.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner(soundcueMenu.getScene().getWindow());
        stage.setScene(new Scene(root));

        stage.showAndWait();
        if ((boolean) MainApp.GLOBAL_DATA.get("HasNewData")){
            soundcueMenu.setText((String) MainApp.GLOBAL_DATA.get("newcuename"));
            cueOrder.setText("");
            soundMenu.setText("Select sound");
            transitionMenu.setText("Select transition");
        }
    }


    private MixerJsonObject.PlayScene getScene(String name, MixerJsonObject source){
        for (MixerJsonObject.PlayScene scene : source.scenes){

            if (scene.name.equals(name)){
                return scene;
            }
        }
        return null;
    }

    @FXML
    void confirmBtnClicked(ActionEvent event) throws IOException {
        if (cueOrder.getText().isEmpty() || startingVol.getText().isEmpty())
            return;



        int curSceneIdx = 0;
        int soundConfIdx = 0;
        for (MixerJsonObject.PlayScene ps : mixerJsonObject.scenes) {
            if (ps.name.equals(MainApp.GLOBAL_DATA.get("scene"))) {
                break;
            }
            curSceneIdx++;
        }
        if (isNewCue) {
            String cueName = soundcueMenu.getText();
            int cOrder = Integer.parseInt(cueOrder.getText());
            int startVol = Integer.parseInt(startingVol.getText());
            String soundFileName = soundMenu.getText();
            int soundId = 0;
            for (MixerJsonObject.SoundFiles sf : mixerJsonObject.listOfSounds) {
                if (sf.soundFile.equals(soundFileName)) {
                    soundId = sf.soundId;
                }
            }
            int tOrder = Integer.parseInt(transOrder.getText());
            String tType = transTypeMenu.getText();
            int tVol = Integer.parseInt(transVol.getText());
            int tTime = Math.round(Float.parseFloat(transTime.getText()) * 1000);

            MixerJsonObject.PlayScene.SoundConfiguration sc = new MixerJsonObject.PlayScene.SoundConfiguration(
                    cOrder, soundId, cueName, startVol,
                    new MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition[]
                            {new MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition(
                                    tOrder, tType, tVol, tTime)}
            );




            if (mixerJsonObject.scenes[curSceneIdx].soundConfiguration == null) {
                mixerJsonObject.scenes[curSceneIdx].soundConfiguration = new MixerJsonObject.PlayScene.SoundConfiguration[0];
            }

            List<MixerJsonObject.PlayScene.SoundConfiguration> soundConfigurations =
                    new ArrayList<>(Arrays.asList(mixerJsonObject.scenes[curSceneIdx].soundConfiguration));
            soundConfigurations.add(sc);
            mixerJsonObject.scenes[curSceneIdx].soundConfiguration = soundConfigurations.toArray(MixerJsonObject.PlayScene.SoundConfiguration[]::new);
            writeToJsonFile();
            ((Stage) startingVol.getScene().getWindow()).close();
        } else {
            String cueName = soundcueMenu.getText();
            int cOrder = Integer.parseInt(cueOrder.getText());
            int startVol = Integer.parseInt(startingVol.getText());
            String soundFileName = soundMenu.getText();
            int soundId = 0;
            for (MixerJsonObject.SoundFiles sf : mixerJsonObject.listOfSounds) {
                if (sf.soundFile.equals(soundFileName)) {
                    soundId = sf.soundId;
                }
            }
            int tOrder = 0, tVol = 0, tTime = 0;
            String tType = null;

            if (!transitionMenu.getText().trim().equals("Select transition")) {
                tOrder = Integer.parseInt(transOrder.getText());
                tType = transTypeMenu.getText();
                tVol = Integer.parseInt(transVol.getText());
                tTime = Math.round(Float.parseFloat(transTime.getText()) * 1000);
            }



            List<MixerJsonObject.PlayScene.SoundConfiguration> soundConfigurations =
                    new ArrayList<>(Arrays.asList(mixerJsonObject.scenes[curSceneIdx].soundConfiguration));

            soundConfIdx = 0;

            for (MixerJsonObject.PlayScene.SoundConfiguration sc : soundConfigurations) {
                if (sc.cueId == curCueOrder) {
                    break;
                }
                soundConfIdx++;
            }

            mixerJsonObject.scenes[curSceneIdx].soundConfiguration[soundConfIdx].soundId = soundId;
            mixerJsonObject.scenes[curSceneIdx].soundConfiguration[soundConfIdx].startVol = startVol;
            mixerJsonObject.scenes[curSceneIdx].soundConfiguration[soundConfIdx].cueId = cOrder;


            if (transitionMenu.getText().equals("New transition")) {
                List<MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition> stList = new ArrayList<>(Arrays.asList(mixerJsonObject.scenes[curSceneIdx].soundConfiguration[soundConfIdx].transitions));
                stList.add(new MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition(
                        tOrder, tType, tVol, tTime
                ));
                mixerJsonObject.scenes[curSceneIdx].soundConfiguration[soundConfIdx].transitions = stList.toArray(MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition[]::new);

            } else if (!transitionMenu.getText().equals("Select transition")) {
                int currentOrder = Integer.parseInt(transitionMenu.getText().split("\\.")[0]);
                for (int i = 0; i < mixerJsonObject.scenes[curSceneIdx].soundConfiguration[soundConfIdx].transitions.length; i++) {
                    if (mixerJsonObject.scenes[curSceneIdx].soundConfiguration[soundConfIdx].transitions[i].transitionId == currentOrder) {
                        mixerJsonObject.scenes[curSceneIdx].soundConfiguration[soundConfIdx].transitions[i].transitionId = tOrder;
                        mixerJsonObject.scenes[curSceneIdx].soundConfiguration[soundConfIdx].transitions[i].transitionType = tType;
                        mixerJsonObject.scenes[curSceneIdx].soundConfiguration[soundConfIdx].transitions[i].transitionTime = tTime;
                        mixerJsonObject.scenes[curSceneIdx].soundConfiguration[soundConfIdx].transitions[i].newVolume = tVol;
                    }
                }

            }
            Arrays.sort(mixerJsonObject.scenes[curSceneIdx].soundConfiguration[soundConfIdx].transitions,
                    new transitionSorter());


        }
        Arrays.sort(mixerJsonObject.scenes[curSceneIdx].soundConfiguration, Comparator.comparingInt(o -> o.cueId));



        writeToJsonFile();
        ((Stage) startingVol.getScene().getWindow()).close();
    }

    class transitionSorter implements Comparator<MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition>{

        @Override
        public int compare(MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition o1, MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition o2) {
            return o1.transitionId - o2.transitionId;
        }
    }

    void writeToJsonFile() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        FileWriter fw = new FileWriter(System.getProperty("user.dir") + "\\data\\" + MainApp.NAME + "\\" + MainApp.NAME + ".json");
        gson.toJson(mixerJsonObject, fw);
        fw.close();
    }
}

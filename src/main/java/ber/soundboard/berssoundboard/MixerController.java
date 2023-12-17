 package ber.soundboard.berssoundboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.util.*;

public class MixerController implements Initializable {
    File mixerDir;
    File mixerConfigFile;

    //<editor-fold desc="FXML variables">
    @FXML
    private Label title;

    @FXML
    private ScrollPane SoundListPane;

    @FXML
    private Label channel1Cue;

    @FXML
    private BorderPane channel1FreeButton;

    @FXML
    private BorderPane channel1PlayButton;

    @FXML
    private BorderPane channel1PredeterTransButton;

    @FXML
    private Label channel1PredeterTransTime;

    @FXML
    private Label channel1PredeterTransVol;

    @FXML
    private Slider channel1Slider;

    @FXML
    private Label channel1Sound;

    @FXML
    private BorderPane channel1TransitionerButton;

    @FXML
    private TextField channel1TransitionerTime;

    @FXML
    private TextField channel1TransitionerVol;

    @FXML
    private Label channel2Cue;

    @FXML
    private BorderPane channel2FreeButton;

    @FXML
    private BorderPane channel2PlayButton;

    @FXML
    private BorderPane channel2PredeterTransButton;

    @FXML
    private Label channel2PredeterTransTime;

    @FXML
    private Label channel2PredeterTransVol;

    @FXML
    private Slider channel2Slider;

    @FXML
    private Label channel2Sound;

    @FXML
    private BorderPane channel2TransitionerButton;

    @FXML
    private TextField channel2TransitionerTime;

    @FXML
    private TextField channel2TransitionerVol;

    @FXML
    private Label channel3Cue;

    @FXML
    private BorderPane channel3FreeButton;

    @FXML
    private BorderPane channel3PlayButton;

    @FXML
    private BorderPane channel3PredeterTransButton;

    @FXML
    private Label channel3PredeterTransTime;

    @FXML
    private Label channel3PredeterTransVol;

    @FXML
    private Slider channel3Slider;

    @FXML
    private Label channel3Sound;

    @FXML
    private BorderPane channel3TransitionerButton;

    @FXML
    private TextField channel3TransitionerTime;

    @FXML
    private TextField channel3TransitionerVol;

    @FXML
    private Label channel4Cue;

    @FXML
    private BorderPane channel4FreeButton;

    @FXML
    private BorderPane channel4PlayButton;

    @FXML
    private BorderPane channel4PredeterTransButton;

    @FXML
    private Label channel4PredeterTransTime;

    @FXML
    private Label channel4PredeterTransVol;

    @FXML
    private Slider channel4Slider;

    @FXML
    private Label channel4Sound;

    @FXML
    private BorderPane channel4TransitionerButton;

    @FXML
    private TextField channel4TransitionerTime;

    @FXML
    private TextField channel4TransitionerVol;

    @FXML
    private Label channel5Cue;

    @FXML
    private BorderPane channel5FreeButton;

    @FXML
    private BorderPane channel5PlayButton;

    @FXML
    private BorderPane channel5PredeterTransButton;

    @FXML
    private Label channel5PredeterTransTime;

    @FXML
    private Label channel5PredeterTransVol;

    @FXML
    private Slider channel5Slider;

    @FXML
    private Label channel5Sound;

    @FXML
    private BorderPane channel5TransitionerButton;

    @FXML
    private TextField channel5TransitionerTime;

    @FXML
    private TextField channel5TransitionerVol;

    @FXML
    private Label channel6Cue;

    @FXML
    private BorderPane channel6FreeButton;

    @FXML
    private BorderPane channel6PlayButton;

    @FXML
    private BorderPane channel6PredeterTransButton;

    @FXML
    private Label channel6PredeterTransTime;

    @FXML
    private Label channel6PredeterTransVol;

    @FXML
    private Slider channel6Slider;

    @FXML
    private Label channel6Sound;

    @FXML
    private BorderPane channel6TransitionerButton;

    @FXML
    private TextField channel6TransitionerTime;

    @FXML
    private TextField channel6TransitionerVol;

    @FXML
    private Label channel7Cue;

    @FXML
    private BorderPane channel7FreeButton;

    @FXML
    private BorderPane channel7PlayButton;

    @FXML
    private BorderPane channel7PredeterTransButton;

    @FXML
    private Label channel7PredeterTransTime;

    @FXML
    private Label channel7PredeterTransVol;

    @FXML
    private Slider channel7Slider;

    @FXML
    private Label channel7Sound;

    @FXML
    private BorderPane channel7TransitionerButton;

    @FXML
    private TextField channel7TransitionerTime;

    @FXML
    private TextField channel7TransitionerVol;

    @FXML
    private Label channel8Cue;

    @FXML
    private BorderPane channel8FreeButton;

    @FXML
    private BorderPane channel8PlayButton;

    @FXML
    private BorderPane channel8PredeterTransButton;

    @FXML
    private Label channel8PredeterTransTime;

    @FXML
    private Label channel8PredeterTransVol;

    @FXML
    private Slider channel8Slider;

    @FXML
    private Label channel8Sound;

    @FXML
    private BorderPane channel8TransitionerButton;

    @FXML
    private TextField channel8TransitionerTime;

    @FXML
    private TextField channel8TransitionerVol;

    @FXML
    private Label mixerCloseButton;

    @FXML
    private MenuButton sceneSelector;

    @FXML
    private GridPane soundCueGridPane;
    //</editor-fold>

    private static MixerJsonObject mixerJsonObject;

    private static MixerJsonObject.PlayScene.SoundConfiguration[] MIXER_CHANNELS =
            new MixerJsonObject.PlayScene.SoundConfiguration[8];
    private static Iterator<MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition>[] transitionIterator
            = new Iterator[]{null, null, null, null, null, null, null, null};

    private static TimerTask[] transitionTasks = new TimerTask[8];

    private Mixer[] mixer;



    @FXML
    void onMouseEnteredExitButton(MouseEvent event){
        mixerCloseButton.setBackground(Background.fill(Color.RED));
    }

    @FXML
    void onMouseExitedExitButton(MouseEvent event){
        mixerCloseButton.setBackground(Background.EMPTY);
    }

    @FXML
    void onMousePressedExitButton(MouseEvent event) throws IOException, InterruptedException {
        updateJsonFile();
        MainApp.DOUT.writeUTF("TERMINATION");
        Thread.sleep(500);
        System.exit(0);
    }


    private void setScene(String scene){
        MainApp.GLOBAL_DATA.put("scene", scene);
        updateSoundCueList();
    }

    @FXML
    void addNewCue(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("transitionPopup.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner(channel1Slider.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.showAndWait();

        refreshJson();
        updateSoundCueList();
    }
    
    void refreshJson() throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(mixerConfigFile);
        mixerJsonObject = gson.fromJson(reader, MixerJsonObject.class);
        reader.close();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mixer = new Mixer[]{
                new Mixer(channel1PlayButton, channel1PredeterTransButton, channel1TransitionerButton, channel1FreeButton,
                        channel1Slider, channel1Sound, channel1Cue, channel1PredeterTransVol, channel1PredeterTransTime, channel1TransitionerVol, channel1TransitionerTime),
                new Mixer(channel2PlayButton, channel2PredeterTransButton, channel2TransitionerButton, channel2FreeButton,
                        channel2Slider, channel2Sound, channel2Cue, channel2PredeterTransVol, channel2PredeterTransTime, channel2TransitionerVol, channel2TransitionerTime),
                new Mixer(channel3PlayButton, channel3PredeterTransButton, channel3TransitionerButton, channel3FreeButton,
                        channel3Slider, channel3Sound, channel3Cue, channel3PredeterTransVol, channel3PredeterTransTime, channel3TransitionerVol, channel3TransitionerTime),
                new Mixer(channel4PlayButton, channel4PredeterTransButton, channel4TransitionerButton, channel4FreeButton,
                        channel4Slider, channel4Sound, channel4Cue, channel4PredeterTransVol, channel4PredeterTransTime, channel4TransitionerVol, channel4TransitionerTime),
                new Mixer(channel5PlayButton, channel5PredeterTransButton, channel5TransitionerButton, channel5FreeButton,
                        channel5Slider, channel5Sound, channel5Cue, channel5PredeterTransVol, channel5PredeterTransTime, channel5TransitionerVol, channel5TransitionerTime),
                new Mixer(channel6PlayButton, channel6PredeterTransButton, channel6TransitionerButton, channel6FreeButton,
                        channel6Slider, channel6Sound, channel6Cue, channel6PredeterTransVol, channel6PredeterTransTime, channel6TransitionerVol, channel6TransitionerTime),
                new Mixer(channel7PlayButton, channel7PredeterTransButton, channel7TransitionerButton, channel7FreeButton,
                        channel7Slider, channel7Sound, channel7Cue, channel7PredeterTransVol, channel7PredeterTransTime, channel7TransitionerVol, channel7TransitionerTime),
                new Mixer(channel8PlayButton, channel8PredeterTransButton, channel8TransitionerButton, channel8FreeButton,
                        channel8Slider, channel8Sound, channel8Cue, channel8PredeterTransVol, channel8PredeterTransTime, channel8TransitionerVol, channel8TransitionerTime),
        };
        title.setText("Phantom - " + MainApp.NAME);
        try {
            MainApp.DOUT.writeUTF("LOADSOUND " + MainApp.NAME);
            MainApp.DOUT.flush();
            while (MainApp.DIN.available() > 0){
                MainApp.DIN.read();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SoundListPane.setPrefWidth((double) (800 * 77) / 300);
        mixerDir = new File("data", MainApp.NAME);
        mixerDir.setLastModified(System.currentTimeMillis());

        mixerConfigFile = new File(System.getProperty("user.dir")+"\\data\\" + MainApp.NAME + "\\" + MainApp.NAME + ".json");
        try {
            refreshJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sortAll();

        try {
            updateSceneList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!Objects.equals(sceneSelector.getItems().get(0).getText(), "+ Add new scene")){
            //If a scene is actually selected
            MainApp.GLOBAL_DATA.put("scene", sceneSelector.getText());
            updateSoundCueList();
        }

        for (int i = 0; i < 8; i++){
            int finalI = i;
            mixer[i].playBtn.setOnMouseEntered(event -> channelPlayButtonEnter(mixer[finalI].playBtn));
            mixer[i].playBtn.setOnMouseExited(event -> channelPlayButtonExit(mixer[finalI].playBtn));
            mixer[i].playBtn.setOnMouseClicked(event -> {
                try {
                    if (event.isShiftDown())
                        channelPlayButtonPressedWithShift(finalI);
                    else
                        channelPlayButtonPressed(finalI, mixer[finalI].volumeSlider.getValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            mixer[i].volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    MainApp.DOUT.writeUTF(String.format("SETVOL %d %d", finalI, newValue.intValue()));
                    MainApp.DOUT.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            mixer[i].predeterTransitionBtn.setOnMouseEntered(event -> greenButtonEntered(mixer[finalI].predeterTransitionBtn));
            mixer[i].predeterTransitionBtn.setOnMouseExited(event -> greenButtonExited(mixer[finalI].predeterTransitionBtn));
            mixer[i].predeterTransitionBtn.setOnMouseClicked(event -> predeterTransButtonPressed(mixer[finalI], finalI));

            mixer[i].transitionerVolume.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*"))
                    mixer[finalI].transitionerVolume.setText(newValue.replaceAll("[^\\d]", ""));
                if (Integer.parseInt(newValue) > 100){
                    mixer[finalI].transitionerVolume.setText(oldValue);
                }
            });

            mixer[i].transitionerTime.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("[0-9.]+"))
                    mixer[finalI].transitionerTime.setText(newValue.replaceAll("[^[0-9.]]", ""));
                if (newValue.startsWith("."))
                    mixer[finalI].transitionerTime.setText(oldValue);

            });

            mixer[i].transitionerBtn.setOnMouseEntered(event -> greenButtonEntered(mixer[finalI].transitionerBtn));
            mixer[i].transitionerBtn.setOnMouseExited(event -> greenButtonExited(mixer[finalI].transitionerBtn));
            mixer[i].transitionerBtn.setOnMouseClicked(event -> transButtonClicked(mixer[finalI], finalI));

            mixer[i].freeBtn.setOnMouseClicked(event -> {
                try {
                    freeChannel(mixer[finalI], finalI);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            mixer[i].volumeSlider.setOnMouseClicked(event -> sliderClicked(finalI));
        }
    }

    private void freeChannel(Mixer mixer, int channel) throws IOException {
        mixer.soundName.setText("N/A");
        mixer.soundCue.setText("N/A");
        mixer.predeterTransitionVolume.setText("N/A");
        mixer.predeterTransitionTime.setText("N/A");

        MIXER_CHANNELS[channel] = null;
        transitionIterator[channel] = null;
        if (transitionTasks[channel] != null)
            transitionTasks[channel].cancel();
        transitionTasks[channel] = null;

        MainApp.DOUT.writeUTF("FREE " + channel);
        MainApp.DOUT.flush();
    }

    private void transButtonClicked(Mixer mixer, int channel) {
        if (mixer.transitionerTime.getText().isEmpty() || mixer.transitionerVolume.getText().isEmpty() || mixer.transitionerTime.getText().endsWith("."))
            return;

        int volume = Integer.parseInt(mixer.transitionerVolume.getText());
        float time = Float.parseFloat(mixer.transitionerTime.getText());

        fade(volume, Math.round(time), mixer.volumeSlider, channel);
    }

    private void predeterTransButtonPressed(Mixer mixer, int channel) {
        if (mixer.predeterTransitionVolume.getText().equals("N/A"))
            return;
        else if (mixer.predeterTransitionVolume.getText().equals("END"))
            fadeOut(Math.round(Float.parseFloat(mixer.predeterTransitionTime.getText())), mixer.volumeSlider, channel);
        else {
            int volume = Integer.parseInt(mixer.predeterTransitionVolume.getText());
            float time = Float.parseFloat(mixer.predeterTransitionTime.getText());
            fade(volume, Math.round(time), mixer.volumeSlider, channel);
        }

        loadNextTransition(mixer, channel);

    }

    private static void loadNextTransition(Mixer mixer, int channel) {
        if (transitionIterator != null && transitionIterator[channel].hasNext()){
            MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition st = transitionIterator[channel].next();
            if (st.transitionType.equals("FADEOUT"))
                mixer.predeterTransitionVolume.setText("END");
            else if (st.transitionType.equals("FADEIN")) {
                mixer.predeterTransitionVolume.setText("S|" + st.newVolume);
                mixer.volumeSlider.setValue(0);
            }
            else
                mixer.predeterTransitionVolume.setText(String.valueOf(st.newVolume));
            mixer.predeterTransitionTime.setText(String.valueOf(st.transitionTime));
        } else {
            mixer.predeterTransitionVolume.setText("N/A");
            mixer.predeterTransitionTime.setText("N/A");
            transitionIterator[channel] = null;
        }
    }

    void fade(int newVolume, int time, Slider slider, int channel){
        if (transitionTasks[channel] != null)
            transitionTasks[channel].cancel();
        transitionTasks[channel] = new TimerTask() {
            int currentVol = (int) slider.getValue();
            final boolean fadeup = newVolume > currentVol;
            @Override
            public void run() {
                if (currentVol == newVolume)
                    cancel();
                if (fadeup)
                    currentVol++;
                else
                    currentVol--;
                slider.setValue(currentVol);
            }
        };
        new Timer().schedule(transitionTasks[channel], 0L, (long) (time/Math.abs(newVolume - slider.getValue())));
    }

    void fadeOut(int time, Slider slider, int channel){
        if (transitionTasks[channel] != null)
            transitionTasks[channel].cancel();
        transitionTasks[channel] = new TimerTask() {
            int currentVol = (int) slider.getValue();
            @Override
            public void run() {
                if (currentVol == 0){
                    try {
                        MainApp.DOUT.writeUTF("STOP " + channel);
                        MainApp.DOUT.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    cancel();
                }
                currentVol--;
                slider.setValue(currentVol);
            }
        };
        new Timer().schedule(transitionTasks[channel], 0L, (long) (time/slider.getValue()));
    }

    private static void sortAll() {
        if (mixerJsonObject.scenes != null && mixerJsonObject.scenes.length > 0){
            for (MixerJsonObject.PlayScene ps : mixerJsonObject.scenes){
                if (ps.soundConfiguration != null && ps.soundConfiguration.length > 0){
                    for (MixerJsonObject.PlayScene.SoundConfiguration sc : ps.soundConfiguration){
                        if (sc.transitions != null && sc.transitions.length > 0){
                            Arrays.sort(sc.transitions, Comparator.comparingInt(o -> o.transitionId));
                        }
                    }
                    Arrays.sort(ps.soundConfiguration, Comparator.comparingInt(o -> o.cueId));
                }
            }
            Arrays.sort(mixerJsonObject.scenes, Comparator.comparingInt(o -> o.sceneIdx));
        }
    }

    void sliderClicked(int channel){
        if (transitionTasks[channel] != null){
            transitionTasks[channel].cancel();
        }
    }

    void channelPlayButtonEnter(BorderPane pane){
        pane.setBackground(Background.fill(Color.color((double) 255 /255, (double) 77 /255, (double) 77 /255)));

    }

    void channelPlayButtonExit(BorderPane pane){
        pane.setBackground(Background.fill(Color.color((double) 79 /255, (double) 33 /255, (double) 40 /255)));
    }
    void greenButtonEntered(BorderPane pane){
        pane.setBackground(Background.fill(Color.color((double) 73 /255, (double) 130 /255, (double) 84 /255)));

    }
    void greenButtonExited(BorderPane pane){
        pane.setBackground(Background.fill(Color.color((double) 112 /255, (double) 196 /255, (double) 129 /255)));
    }

    void channelPlayButtonPressed(int channel, double sliderValue) throws IOException {
        MainApp.DOUT.writeUTF("PLAY " + channel);
        MainApp.DOUT.flush();
        if (mixer[channel].predeterTransitionVolume.getText().contains("S|")){
            int newVolume = Integer.parseInt(mixer[channel].predeterTransitionVolume.getText().replace("S|", ""));
            int time = Integer.parseInt(mixer[channel].predeterTransitionTime.getText());
            fade(newVolume, time, mixer[channel].volumeSlider, channel);
            loadNextTransition(mixer[channel], channel);
            return;
        }
        MainApp.DOUT.writeUTF("SETVOL " + channel + " " + sliderValue);
        MainApp.DOUT.flush();
    }

    void channelPlayButtonPressedWithShift(int channel) throws IOException{
        MainApp.DOUT.writeUTF("STOP " + channel);
        MainApp.DOUT.flush();
    }


    private void updateSoundCueList(){
        soundCueGridPane.getChildren().clear();
        soundCueGridPane.setPrefHeight(20);
        Label soundcueLabel = new Label("Soundcues");
        soundcueLabel.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
        soundcueLabel.setPadding(new Insets(10, 10 , 10 ,10));
        soundCueGridPane.addRow(0, soundcueLabel);
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(Integer.MAX_VALUE);
        soundCueGridPane.addRow(1, line);

        MixerJsonObject.PlayScene currentlySelectedScene = null;
        for (MixerJsonObject.PlayScene playScene : mixerJsonObject.scenes){
            if (playScene.name.equals(MainApp.GLOBAL_DATA.get("scene"))){
                currentlySelectedScene = playScene;
            }
        }
        if (currentlySelectedScene.soundConfiguration == null || currentlySelectedScene.soundConfiguration.length == 0){
            return;
        }
        int i = 0;
        for(MixerJsonObject.PlayScene.SoundConfiguration soundConfig : currentlySelectedScene.soundConfiguration){
            i++;
            soundCueGridPane.setPrefHeight(soundCueGridPane.getHeight() + 20);
            Label newSoundCueText = getNewSoundCueTextLabel(soundConfig, i);
            soundCueGridPane.addRow(soundCueGridPane.getRowCount(), newSoundCueText);
        }
    }

    private Label getNewSoundCueTextLabel(MixerJsonObject.PlayScene.SoundConfiguration soundConfig, int i) {
        Label newSoundCueText = new Label(i + ". " + soundConfig.cueName);
        newSoundCueText.setOnMouseEntered(event -> {
            newSoundCueText.setBackground(Background.fill(Color.LIGHTGRAY));
        });
        newSoundCueText.setOnMouseExited(event -> {
            newSoundCueText.setBackground(Background.EMPTY);
        });
        newSoundCueText.setOnMouseClicked(event -> {
            try {
                addSoundToMixer(soundConfig);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        newSoundCueText.setPadding(new Insets(3, 0, 0, 3));
        return newSoundCueText;
    }

    void addSoundToMixer(MixerJsonObject.PlayScene.SoundConfiguration config) throws IOException {
        for (int i = 0; i < 8; i++){
            if (MIXER_CHANNELS[i] == null) {
                MIXER_CHANNELS[i] = config;
                mixer[i].soundCue.setText(config.cueName);
                mixer[i].soundName.setText(getSoundFromId(config.soundId).soundFile.split("\\.")[0]);

                if (config.transitions != null && config.transitions.length > 0) {
                    transitionIterator[i] = Arrays.stream(config.transitions).iterator();
                    loadNextTransition(mixer[i], i);
                    if (!config.transitions[0].transitionType.equals("FADEIN"))
                        mixer[i].volumeSlider.setValue(config.transitions[0].newVolume);
                    else
                        mixer[i].volumeSlider.setValue(0);
                } else {
                    mixer[i].volumeSlider.setValue(config.startVol);
                }
                MainApp.DOUT.writeUTF(String.format("SOUNDADDTOMIXER %d %d %d %d", i, config.soundId, config.loop, config.startVol));
                MainApp.DOUT.flush();
                break;
            }
        }
    }

    MixerJsonObject.SoundFiles getSoundFromId(int id){
        for (MixerJsonObject.SoundFiles sf : mixerJsonObject.listOfSounds){
            if (sf.soundId == id){
                return sf;
            }
        }
        return null;
    }

    private void updateSceneList() throws IOException {
        sceneSelector.getItems().clear();
        sortAll();
        int i = mixerJsonObject.scenes.length;
        if (i > 0){ //If scene already exists
            for (int j = 0; j < i; j++){
                MenuItem newMenuItem = new MenuItem(mixerJsonObject.scenes[j].name);

                newMenuItem.setOnAction((event) -> {
                    setScene(newMenuItem.getText());
                    sceneSelector.setText(newMenuItem.getText());
                    updateSoundCueList();
                });
                sceneSelector.getItems().add(newMenuItem);
            }
        }
        MenuItem addButtonMenuItem = new MenuItem("+ Add new scene");
        addButtonMenuItem.setOnAction((event -> {
            final Stage newPopup = new Stage();
            newPopup.initModality(Modality.APPLICATION_MODAL);
            Stage parentStage = (Stage) sceneSelector.getScene().getWindow();
            newPopup.initOwner(parentStage);
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("newscenepopup.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene popupScene = new Scene(root);
            newPopup.initStyle(StageStyle.UNDECORATED);
            newPopup.setScene(popupScene);
            newPopup.showAndWait();
            if (MainApp.GLOBAL_DATA.get("HasNewData").equals("false"))
                return;
            MainApp.GLOBAL_DATA.put("HasNewData", "false");
            List<MixerJsonObject.PlayScene> arrList = Arrays.asList(mixerJsonObject.scenes);
            List<MixerJsonObject.PlayScene> modableList = new ArrayList<>(arrList);
            modableList.add(new MixerJsonObject.PlayScene(
                    MainApp.GLOBAL_DATA.get("NewSceneName").toString(),
                    Integer.parseInt(MainApp.GLOBAL_DATA.get("NewSceneNumber").toString())
            ));
            mixerJsonObject.scenes = modableList.toArray(new MixerJsonObject.PlayScene[arrList.size()]);
            try {
                updateSceneList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setScene(MainApp.GLOBAL_DATA.get("NewSceneName").toString());
        }));

        sceneSelector.getItems().add(addButtonMenuItem);
        sceneSelector.setText(sceneSelector.getItems().get(0).getText());
        updateJsonFile();
    }

    private void updateJsonFile() throws IOException {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\data\\" + MainApp.NAME + "\\" + MainApp.NAME + ".json");
        gson.toJson(mixerJsonObject, writer);
        writer.close();
    }

    @FXML
    void onButtonClickNewSound (MouseEvent event) throws IOException {
        final Stage newPopup = new Stage();
        newPopup.initModality(Modality.APPLICATION_MODAL);
        Stage parentStage = (Stage) sceneSelector.getScene().getWindow();
        newPopup.initOwner(parentStage);
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("newsoundpopup.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene popupScene = new Scene(root);
        newPopup.initStyle(StageStyle.UNDECORATED);
        newPopup.setScene(popupScene);
        newPopup.showAndWait();
        if (MainApp.GLOBAL_DATA.get("HasNewData").equals("false"))
            return;
        MainApp.GLOBAL_DATA.put("HasNewData", "false");
        MainApp.DOUT.writeUTF("LOADSOUND " + MainApp.NAME);
    }

}

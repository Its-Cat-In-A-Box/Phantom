package ber.soundboard.berssoundboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
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

    private static int[] MIXER_CHANNEL_TRANSITION_IDX = new int[]{0,0,0,0,0,0,0,0};

    private static TimerTask[] transitionTasks = new TimerTask[8];



    @FXML
    void onMouseEnteredExitButton(MouseEvent event){
        mixerCloseButton.setBackground(Background.fill(Color.RED));
    }

    @FXML
    void onMouseExitedExitButton(MouseEvent event){
        mixerCloseButton.setBackground(Background.EMPTY);
    }

    @FXML
    void onMousePressedExitButton(MouseEvent event) throws IOException {
        updateJsonFile();
        MainApp.DOUT.writeUTF("TERMINATION");
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

        Gson gson = new Gson();
        FileReader reader = new FileReader(mixerConfigFile);
        mixerJsonObject = gson.fromJson(reader, MixerJsonObject.class);
        reader.close();

        updateSoundCueList();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(mixerConfigFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        mixerJsonObject = gson.fromJson(reader, MixerJsonObject.class);


        if (mixerJsonObject.scenes != null && mixerJsonObject.scenes.length > 0){
            for (MixerJsonObject.PlayScene ps : mixerJsonObject.scenes){
                if (ps.soundConfiguration != null && ps.soundConfiguration.length > 0){
                    for (MixerJsonObject.PlayScene.SoundConfiguration sc : ps.soundConfiguration){
                        Arrays.sort(sc.transitions, Comparator.comparingInt(o -> o.transitionId));
                    }
                    Arrays.sort(ps.soundConfiguration, Comparator.comparingInt(o -> o.cueId));
                }
            }
            Arrays.sort(mixerJsonObject.scenes, Comparator.comparingInt(o -> o.sceneIdx));
        }


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

        //<editor-fold desc="Play Button On Mouse Entered">
        channel1PlayButton.setOnMouseEntered(event -> channelPlayButtonEnter(channel1PlayButton));
        channel2PlayButton.setOnMouseEntered(event -> channelPlayButtonEnter(channel2PlayButton));
        channel3PlayButton.setOnMouseEntered(event -> channelPlayButtonEnter(channel3PlayButton));
        channel4PlayButton.setOnMouseEntered(event -> channelPlayButtonEnter(channel4PlayButton));
        channel5PlayButton.setOnMouseEntered(event -> channelPlayButtonEnter(channel5PlayButton));
        channel6PlayButton.setOnMouseEntered(event -> channelPlayButtonEnter(channel6PlayButton));
        channel7PlayButton.setOnMouseEntered(event -> channelPlayButtonEnter(channel7PlayButton));
        channel8PlayButton.setOnMouseEntered(event -> channelPlayButtonEnter(channel8PlayButton));
        //</editor-fold>

        //<editor-fold desc="Play Button On Mouse Exited">
        channel1PlayButton.setOnMouseExited(event -> channelPlayButtonExit(channel1PlayButton));
        channel2PlayButton.setOnMouseExited(event -> channelPlayButtonExit(channel2PlayButton));
        channel3PlayButton.setOnMouseExited(event -> channelPlayButtonExit(channel3PlayButton));
        channel4PlayButton.setOnMouseExited(event -> channelPlayButtonExit(channel4PlayButton));
        channel5PlayButton.setOnMouseExited(event -> channelPlayButtonExit(channel5PlayButton));
        channel6PlayButton.setOnMouseExited(event -> channelPlayButtonExit(channel6PlayButton));
        channel7PlayButton.setOnMouseExited(event -> channelPlayButtonExit(channel7PlayButton));
        channel8PlayButton.setOnMouseExited(event -> channelPlayButtonExit(channel8PlayButton));
        //</editor-fold>

        //<editor-fold desc="Play Button On Mouse Click">
        channel1PlayButton.setOnMouseClicked(event -> {
            try{
                if (event.isShiftDown()){
                    channelPlayButtonPressedWithShift(0);
                } else {
                    channelPlayButtonPressed(0, channel1Slider.getValue());
                }
            } catch (IOException ignored){

            }
        });

        channel2PlayButton.setOnMouseClicked(event -> {
            try{
                if (event.isShiftDown()){
                    channelPlayButtonPressedWithShift(1);
                } else {
                    channelPlayButtonPressed(1, channel2Slider.getValue());
                }
            } catch (IOException e){

            }
        });

        channel3PlayButton.setOnMouseClicked(event -> {
            try{
                if (event.isShiftDown()){
                    channelPlayButtonPressedWithShift(2);
                } else {
                    channelPlayButtonPressed(2, channel3Slider.getValue());
                }
            } catch (IOException e){

            }
        });

        channel4PlayButton.setOnMouseClicked(event -> {
            try{
                if (event.isShiftDown()){
                    channelPlayButtonPressedWithShift(3);
                } else {
                    channelPlayButtonPressed(3, channel4Slider.getValue());
                }
            } catch (IOException e){

            }
        });

        channel5PlayButton.setOnMouseClicked(event -> {
            try{
                if (event.isShiftDown()){
                    channelPlayButtonPressedWithShift(4);
                } else {
                    channelPlayButtonPressed(4, channel5Slider.getValue());
                }
            } catch (IOException e){

            }
        });

        channel6PlayButton.setOnMouseClicked(event -> {
            try{
                if (event.isShiftDown()){
                    channelPlayButtonPressedWithShift(5);
                } else {
                    channelPlayButtonPressed(5, channel6Slider.getValue());
                }
            } catch (IOException e){

            }
        });

        channel7PlayButton.setOnMouseClicked(event -> {
            try{
                if (event.isShiftDown()){
                    channelPlayButtonPressedWithShift(6);
                } else {
                    channelPlayButtonPressed(6, channel7Slider.getValue());
                }
            } catch (IOException e){

            }
        });

        channel8PlayButton.setOnMouseClicked(event -> {
            try{
                if (event.isShiftDown()){
                    channelPlayButtonPressedWithShift(7);
                } else {
                    channelPlayButtonPressed(7, channel8Slider.getValue());
                }
            } catch (IOException e){

            }
        });
        //</editor-fold>

        //<editor-fold desc="Play Button On Slider Change">
        channel1Slider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                MainApp.DOUT.writeUTF("SETVOL 0 " + newValue);
                MainApp.DOUT.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        channel2Slider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                MainApp.DOUT.writeUTF("SETVOL 1 " + newValue);
                MainApp.DOUT.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        channel3Slider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                MainApp.DOUT.writeUTF("SETVOL 2 " + newValue);
                MainApp.DOUT.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        channel4Slider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                MainApp.DOUT.writeUTF("SETVOL 3 " + newValue);
                MainApp.DOUT.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        channel5Slider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                MainApp.DOUT.writeUTF("SETVOL 4 " + newValue);
                MainApp.DOUT.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        channel6Slider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                MainApp.DOUT.writeUTF("SETVOL 5 " + newValue);
                MainApp.DOUT.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        channel7Slider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                MainApp.DOUT.writeUTF("SETVOL 6 " + newValue);
                MainApp.DOUT.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        channel8Slider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                MainApp.DOUT.writeUTF("SETVOL 7 " + newValue);
                MainApp.DOUT.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        //</editor-fold>

        //<editor-fold desc="Predetermined Transition Button Entered">
        channel1PredeterTransButton.setOnMouseEntered(event ->
                greenButtonEntered(channel1PredeterTransButton));
        channel2PredeterTransButton.setOnMouseEntered(event ->
                greenButtonEntered(channel2PredeterTransButton));
        channel3PredeterTransButton.setOnMouseEntered(event ->
                greenButtonEntered(channel3PredeterTransButton));
        channel4PredeterTransButton.setOnMouseEntered(event ->
                greenButtonEntered(channel4PredeterTransButton));
        channel5PredeterTransButton.setOnMouseEntered(event ->
                greenButtonEntered(channel5PredeterTransButton));
        channel6PredeterTransButton.setOnMouseEntered(event ->
                greenButtonEntered(channel6PredeterTransButton));
        channel7PredeterTransButton.setOnMouseEntered(event ->
                greenButtonEntered(channel7PredeterTransButton));
        channel8PredeterTransButton.setOnMouseEntered(event ->
                greenButtonEntered(channel8PredeterTransButton));
        //</editor-fold>

        //<editor-fold desc="Predetermined Transition Button Exited">
        channel1PredeterTransButton.setOnMouseExited(event ->
                greenButtonExited(channel1PredeterTransButton));
        channel2PredeterTransButton.setOnMouseExited(event ->
                greenButtonExited(channel2PredeterTransButton));
        channel3PredeterTransButton.setOnMouseExited(event ->
                greenButtonExited(channel3PredeterTransButton));
        channel4PredeterTransButton.setOnMouseExited(event ->
                greenButtonExited(channel4PredeterTransButton));
        channel5PredeterTransButton.setOnMouseExited(event ->
                greenButtonExited(channel5PredeterTransButton));
        channel6PredeterTransButton.setOnMouseExited(event ->
                greenButtonExited(channel6PredeterTransButton));
        channel7PredeterTransButton.setOnMouseExited(event ->
                greenButtonExited(channel7PredeterTransButton));
        channel8PredeterTransButton.setOnMouseExited(event ->
                greenButtonExited(channel8PredeterTransButton));
        //</editor-fold>

        //<editor-fold desc="Predetermined Transition Button Clicked">
        channel1PredeterTransButton.setOnMouseClicked(event ->
        {
            try {
                channelPredeterTransButtonPressed(0,
                        channel1PredeterTransVol,
                        channel1PredeterTransTime,
                        channel1Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel2PredeterTransButton.setOnMouseClicked(event ->
        {
            try {
                channelPredeterTransButtonPressed(1,
                        channel2PredeterTransVol,
                        channel2PredeterTransTime,
                        channel2Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel3PredeterTransButton.setOnMouseClicked(event ->
        {
            try {
                channelPredeterTransButtonPressed(2,
                        channel3PredeterTransVol,
                        channel3PredeterTransTime,
                        channel3Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel4PredeterTransButton.setOnMouseClicked(event ->
        {
            try {
                channelPredeterTransButtonPressed(3,
                        channel4PredeterTransVol,
                        channel4PredeterTransTime,
                        channel4Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel5PredeterTransButton.setOnMouseClicked(event ->
        {
            try {
                channelPredeterTransButtonPressed(4,
                        channel5PredeterTransVol,
                        channel5PredeterTransTime,
                        channel5Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel6PredeterTransButton.setOnMouseClicked(event ->
        {
            try {
                channelPredeterTransButtonPressed(5,
                        channel6PredeterTransVol,
                        channel6PredeterTransTime,
                        channel6Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel7PredeterTransButton.setOnMouseClicked(event ->
        {
            try {
                channelPredeterTransButtonPressed(6,
                        channel7PredeterTransVol,
                        channel7PredeterTransTime,
                        channel7Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel8PredeterTransButton.setOnMouseClicked(event ->
        {
            try {
                channelPredeterTransButtonPressed(7,
                        channel8PredeterTransVol,
                        channel8PredeterTransTime,
                        channel8Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //</editor-fold>

        //<editor-fold desc="Free button Clicked">
        channel1FreeButton.setOnMouseClicked(event -> {
            try {
                freeButtonClicked(
                        0, channel1Sound, channel1Cue, channel1PredeterTransVol, channel1PredeterTransTime
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel2FreeButton.setOnMouseClicked(event -> {
            try {
                freeButtonClicked(
                        1, channel2Sound, channel2Cue, channel2PredeterTransVol, channel2PredeterTransTime
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel3FreeButton.setOnMouseClicked(event -> {
            try {
                freeButtonClicked(
                        2, channel3Sound, channel1Cue, channel1PredeterTransVol, channel1PredeterTransTime
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel4FreeButton.setOnMouseClicked(event -> {
            try {
                freeButtonClicked(
                        3, channel4Sound, channel1Cue, channel1PredeterTransVol, channel1PredeterTransTime
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel5FreeButton.setOnMouseClicked(event -> {
            try {
                freeButtonClicked(
                        4, channel5Sound, channel1Cue, channel1PredeterTransVol, channel1PredeterTransTime
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel6FreeButton.setOnMouseClicked(event -> {
            try {
                freeButtonClicked(
                        5, channel6Sound, channel1Cue, channel1PredeterTransVol, channel1PredeterTransTime
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel7FreeButton.setOnMouseClicked(event -> {
            try {
                freeButtonClicked(
                        6, channel7Sound, channel1Cue, channel1PredeterTransVol, channel1PredeterTransTime
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel8FreeButton.setOnMouseClicked(event -> {
            try {
                freeButtonClicked(
                        7, channel8Sound, channel1Cue, channel1PredeterTransVol, channel1PredeterTransTime
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //</editor-fold>

        //<editor-fold desc="Slider Clicked">
        channel1Slider.setOnMouseClicked((event -> {sliderClicked(0);}));
        channel2Slider.setOnMouseClicked((event -> {sliderClicked(1);}));
        channel3Slider.setOnMouseClicked((event -> {sliderClicked(2);}));
        channel4Slider.setOnMouseClicked((event -> {sliderClicked(3);}));
        channel5Slider.setOnMouseClicked((event -> {sliderClicked(4);}));
        channel6Slider.setOnMouseClicked((event -> {sliderClicked(5);}));
        channel7Slider.setOnMouseClicked((event -> {sliderClicked(6);}));
        channel8Slider.setOnMouseClicked((event -> {sliderClicked(7);}));
        //</editor-fold>

        //<editor-fold desc="Transition volume changed">
        channel1TransitionerVol.textProperty().addListener((observable, oldValue, newValue) -> transitionerVolEdited(oldValue, newValue, channel1TransitionerVol));
        channel3TransitionerVol.textProperty().addListener((observable, oldValue, newValue) -> transitionerVolEdited(oldValue, newValue, channel3TransitionerVol));
        channel4TransitionerVol.textProperty().addListener((observable, oldValue, newValue) -> transitionerVolEdited(oldValue, newValue, channel4TransitionerVol));
        channel2TransitionerVol.textProperty().addListener((observable, oldValue, newValue) -> transitionerVolEdited(oldValue, newValue, channel2TransitionerVol));
        channel5TransitionerVol.textProperty().addListener((observable, oldValue, newValue) -> transitionerVolEdited(oldValue, newValue, channel5TransitionerVol));
        channel6TransitionerVol.textProperty().addListener((observable, oldValue, newValue) -> transitionerVolEdited(oldValue, newValue, channel6TransitionerVol));
        channel7TransitionerVol.textProperty().addListener((observable, oldValue, newValue) -> transitionerVolEdited(oldValue, newValue, channel7TransitionerVol));
        channel8TransitionerVol.textProperty().addListener((observable, oldValue, newValue) -> transitionerVolEdited(oldValue, newValue, channel8TransitionerVol));
        //</editor-fold>

        //<editor-fold desc="Transition time changed">
        channel1TransitionerTime.textProperty().addListener((observable, oldValue, newValue) -> transitionTimeEdited(newValue, channel1TransitionerTime));
        channel2TransitionerTime.textProperty().addListener((observable, oldValue, newValue) -> transitionTimeEdited(newValue, channel2TransitionerTime));
        channel3TransitionerTime.textProperty().addListener((observable, oldValue, newValue) -> transitionTimeEdited(newValue, channel3TransitionerTime));
        channel4TransitionerTime.textProperty().addListener((observable, oldValue, newValue) -> transitionTimeEdited(newValue, channel4TransitionerTime));
        channel5TransitionerTime.textProperty().addListener((observable, oldValue, newValue) -> transitionTimeEdited(newValue, channel5TransitionerTime));
        channel6TransitionerTime.textProperty().addListener((observable, oldValue, newValue) -> transitionTimeEdited(newValue, channel6TransitionerTime));
        channel7TransitionerTime.textProperty().addListener((observable, oldValue, newValue) -> transitionTimeEdited(newValue, channel7TransitionerTime));
        channel8TransitionerTime.textProperty().addListener((observable, oldValue, newValue) -> transitionTimeEdited(newValue, channel8TransitionerTime));
        //</editor-fold>

        //<editor-fold desc="Transitioner button entered">
        channel1TransitionerButton.setOnMouseEntered(event -> greenButtonEntered(channel1TransitionerButton));
        channel2TransitionerButton.setOnMouseEntered(event -> greenButtonEntered(channel2TransitionerButton));
        channel3TransitionerButton.setOnMouseEntered(event -> greenButtonEntered(channel3TransitionerButton));
        channel4TransitionerButton.setOnMouseEntered(event -> greenButtonEntered(channel4TransitionerButton));
        channel5TransitionerButton.setOnMouseEntered(event -> greenButtonEntered(channel5TransitionerButton));
        channel6TransitionerButton.setOnMouseEntered(event -> greenButtonEntered(channel6TransitionerButton));
        channel7TransitionerButton.setOnMouseEntered(event -> greenButtonEntered(channel7TransitionerButton));
        channel8TransitionerButton.setOnMouseEntered(event -> greenButtonEntered(channel8TransitionerButton));
        //</editor-fold>

        //<editor-fold desc="Transition button exited">
        channel1TransitionerButton.setOnMouseExited(event -> greenButtonExited(channel1TransitionerButton));
        channel2TransitionerButton.setOnMouseExited(event -> greenButtonExited(channel2TransitionerButton));
        channel3TransitionerButton.setOnMouseExited(event -> greenButtonExited(channel3TransitionerButton));
        channel4TransitionerButton.setOnMouseExited(event -> greenButtonExited(channel4TransitionerButton));
        channel5TransitionerButton.setOnMouseExited(event -> greenButtonExited(channel5TransitionerButton));
        channel6TransitionerButton.setOnMouseExited(event -> greenButtonExited(channel6TransitionerButton));
        channel7TransitionerButton.setOnMouseExited(event -> greenButtonExited(channel7TransitionerButton));
        channel8TransitionerButton.setOnMouseExited(event -> greenButtonExited(channel8TransitionerButton));
        //</editor-fold>

        //<editor-fold desc="Transition button clicked">
        channel1TransitionerButton.setOnMouseClicked(event -> {
            try {
                transitionButtonClicked(0, channel1TransitionerVol, channel1TransitionerTime, channel1Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel2TransitionerButton.setOnMouseClicked(event -> {
            try {
                transitionButtonClicked(1, channel2TransitionerVol, channel2TransitionerTime, channel2Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel3TransitionerButton.setOnMouseClicked(event -> {
            try {
                transitionButtonClicked(2, channel3TransitionerVol, channel3TransitionerTime, channel3Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel4TransitionerButton.setOnMouseClicked(event -> {
            try {
                transitionButtonClicked(3, channel4TransitionerVol, channel4TransitionerTime, channel4Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel5TransitionerButton.setOnMouseClicked(event -> {
            try {
                transitionButtonClicked(4, channel5TransitionerVol, channel5TransitionerTime, channel5Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel6TransitionerButton.setOnMouseClicked(event -> {
            try {
                transitionButtonClicked(5, channel6TransitionerVol, channel6TransitionerTime, channel6Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel7TransitionerButton.setOnMouseClicked(event -> {
            try {
                transitionButtonClicked(6, channel7TransitionerVol, channel7TransitionerTime, channel7Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        channel8TransitionerButton.setOnMouseClicked(event -> {
            try {
                transitionButtonClicked(7, channel8TransitionerVol, channel8TransitionerTime, channel8Slider);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //</editor-fold>

    }

    void transitionButtonClicked(int channel, TextField volT, TextField timeT, Slider slider) throws IOException {
        Long curVolumeL = Math.round(slider.getValue());
        int currentVol = curVolumeL.intValue();
        int newVolume = Integer.parseInt(volT.getText());
        float time = Float.parseFloat(timeT.getText()) * 1000;

        if (newVolume == -1){
            MainApp.DOUT.writeUTF("FADEOUT " + channel + " " + time);
            MainApp.DOUT.flush();
        }

        if (newVolume > currentVol) {//Increasing volume
            transitionTasks[channel] = new TimerTask() {
                int curVol = currentVol;
                final int newVol = newVolume;
                final Slider volSlider = slider;

                @Override
                public void run() {
                    curVol++;
                    volSlider.setValue(curVol);
                    if (curVol == newVol) {
                        this.cancel();
                    }
                }
            };
            new Timer().schedule(transitionTasks[channel], 0L, (long) (time / (newVolume - currentVol)));
        }
        else {
            transitionTasks[channel] = new TimerTask() {
                int curVol = currentVol;
                int newVol = newVolume;
                Slider volSlider = slider;

                @Override
                public void run() {
                    curVol--;
                    volSlider.setValue(curVol);
                    if (curVol == newVol) {
                        this.cancel();
                    }
                }
            };
            new Timer().schedule(transitionTasks[channel], 0L, (long) (time / (currentVol - newVolume)));
        }
    }

    void transitionerVolEdited(String oldValue, String newValue, TextField tf){
        if (!newValue.matches("[0-9-]+")){
            tf.setText(newValue.replaceAll("[^[0-9-]]", ""));
        }
        if (newValue.length() > 3){
            tf.setText(oldValue);
        }
    }

    void transitionTimeEdited(String newValue, TextField tf){
        if (!newValue.matches("[0-9.]+")){
            tf.setText(newValue.replaceAll("[^[0-9.]]", ""));
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



    void freeButtonClicked(int channel, Label sound, Label cue, Label predeterVol, Label predeterTransTime) throws IOException {
        MainApp.DOUT.writeUTF("FREE " + channel);
        MainApp.DOUT.flush();
        MIXER_CHANNELS[channel] = null;
        MIXER_CHANNEL_TRANSITION_IDX[channel] = 0;

        sound.setText("N/A");
        cue.setText("N/A");
        predeterVol.setText("0");
        predeterTransTime.setText("0 ms");
    }


    void channelPlayButtonPressed(int channel, double sliderValue) throws IOException {
        MainApp.DOUT.writeUTF("PLAY " + channel);
        MainApp.DOUT.flush();
        MainApp.DOUT.writeUTF("SETVOL " + channel + " " + sliderValue);
        MainApp.DOUT.flush();

    }

    void channelPlayButtonPressedWithShift(int channel) throws IOException{
        MainApp.DOUT.writeUTF("STOP " + channel);
        MainApp.DOUT.flush();
    }

    void channelPredeterTransButtonPressed(int channel, Label vol, Label time, Slider slider) throws IOException {
        MixerJsonObject.PlayScene.SoundConfiguration channelSoundConfig = MIXER_CHANNELS[channel];
        if (channelSoundConfig.transitions.length == MIXER_CHANNEL_TRANSITION_IDX[channel])
            return;
        //Get next transition
        MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition nextTrans = channelSoundConfig.transitions[MIXER_CHANNEL_TRANSITION_IDX[channel]];
        if (nextTrans.transitionType.equals("FADEOUT")){
            MainApp.DOUT.writeUTF("FADEOUT " + channel + " " + nextTrans.transitionTime);
            MainApp.DOUT.flush();
        }
        Long currentVolL = Math.round(slider.getValue());
        int currentVol = currentVolL.intValue();
        int newVolume = nextTrans.newVolume;
        System.out.println(currentVol + " " + newVolume);
        if (transitionTasks[channel] != null && !nextTrans.transitionType.equals("FADEOUT")){
            transitionTasks[channel].cancel();
        }
        if (newVolume > currentVol) {//Increasing volume
            transitionTasks[channel] = new TimerTask() {
                int curVol = currentVol;
                final int newVol = newVolume;
                final Slider volSlider = slider;

                @Override
                public void run() {
                    curVol++;
                    volSlider.setValue(curVol);
                    if (curVol == newVol) {
                        this.cancel();
                    }
                }
            };
            new Timer().schedule(transitionTasks[channel], 0, nextTrans.transitionTime / (newVolume - currentVol));
        }
        else {
            transitionTasks[channel] = new TimerTask() {
                int curVol = currentVol;
                int newVol = newVolume;
                Slider volSlider = slider;

                @Override
                public void run() {
                    curVol--;
                    volSlider.setValue(curVol);
                    if (curVol == newVol) {
                        this.cancel();
                    }
                }
            };
            new Timer().schedule(transitionTasks[channel], 0, nextTrans.transitionTime / (currentVol - newVolume));
        }
        MIXER_CHANNEL_TRANSITION_IDX[channel]++;
        if (channelSoundConfig.transitions.length == MIXER_CHANNEL_TRANSITION_IDX[channel]){
            vol.setText("0");
            time.setText("0 ms");
            return;
        }
        MixerJsonObject.PlayScene.SoundConfiguration.SoundTransition newTrans = channelSoundConfig.transitions[MIXER_CHANNEL_TRANSITION_IDX[channel]];
        vol.setText(newTrans.transitionType.equals("FADEOUT") ? "END" : String.valueOf(newTrans.newVolume));
        time.setText(newTrans.transitionTime + " ms");
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
                autoAddSoundToMixer(soundConfig);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        newSoundCueText.setPadding(new Insets(3, 0, 0, 3));
        return newSoundCueText;
    }


    private void autoAddSoundToMixer(MixerJsonObject.PlayScene.SoundConfiguration soundConfig) throws IOException {
        for (int i = 0; i < 8; i++){
            if (MIXER_CHANNELS[i] == null){
                MIXER_CHANNELS[i] = soundConfig; // Add sound to server
                if (soundConfig.transitions.length != 0){
                    if (soundConfig.transitions[0].transitionType.equals("FADEIN")){
                        // If the soundcue has a fadein
                        // SOUNDADDTOMIXER channel soundId loops startingVol fadeIn fadeInMs
                        MainApp.DOUT.writeUTF("SOUNDADDTOMIXER " + i + " " + soundConfig.soundId + " " +
                                soundConfig.loop + " " + soundConfig.startVol + " True " +
                                soundConfig.transitions[0].transitionTime);
                        MainApp.DOUT.flush();
                        MIXER_CHANNEL_TRANSITION_IDX[i]++;
                    }
                    else {
                        // SOUNDADDTOMIXER channel soundId loops startingVol
                        MainApp.DOUT.writeUTF("SOUNDADDTOMIXER " + i + " " + soundConfig.soundId + " " +
                                soundConfig.loop + " " + soundConfig.startVol + " False");
                        MainApp.DOUT.flush();
                    }
                }

                // Add sound to client
                switch (i){
                    case 0:
                        channel1Slider.setValue(soundConfig.startVol);
                        channel1Sound.setText(String.valueOf(soundConfig.soundId));
                        channel1Cue.setText(soundConfig.cueName);
                        if (soundConfig.transitions.length != 0)
                            setTransitionText(soundConfig, channel1PredeterTransVol, channel1PredeterTransTime);
                        break;
                    case 1:
                        channel2Slider.setValue(soundConfig.startVol);
                        channel2Sound.setText(String.valueOf(soundConfig.soundId));
                        channel2Cue.setText(soundConfig.cueName);
                        if (soundConfig.transitions.length != 0)
                            setTransitionText(soundConfig, channel2PredeterTransVol, channel2PredeterTransTime);
                        break;
                    case 2:
                        channel3Slider.setValue(soundConfig.startVol);
                        channel3Sound.setText(String.valueOf(soundConfig.soundId));
                        channel3Cue.setText(soundConfig.cueName);
                        if (soundConfig.transitions.length != 0)
                            setTransitionText(soundConfig, channel3PredeterTransVol, channel3PredeterTransTime);
                        break;
                    case 3:
                        channel4Slider.setValue(soundConfig.startVol);
                        channel4Sound.setText(String.valueOf(soundConfig.soundId));
                        channel4Cue.setText(soundConfig.cueName);
                        if (soundConfig.transitions.length != 0)
                            setTransitionText(soundConfig, channel4PredeterTransVol, channel4PredeterTransTime);
                        break;
                    case 4:
                        channel5Slider.setValue(soundConfig.startVol);
                        channel5Sound.setText(String.valueOf(soundConfig.soundId));
                        channel5Cue.setText(soundConfig.cueName);
                        if (soundConfig.transitions.length != 0)
                            setTransitionText(soundConfig, channel5PredeterTransVol, channel5PredeterTransTime);
                        break;
                    case 5:
                        channel6Slider.setValue(soundConfig.startVol);
                        channel6Sound.setText(String.valueOf(soundConfig.soundId));
                        channel6Cue.setText(soundConfig.cueName);
                        if (soundConfig.transitions.length != 0)
                            setTransitionText(soundConfig, channel6PredeterTransVol, channel6PredeterTransTime);
                        break;
                    case 6:
                        channel7Slider.setValue(soundConfig.startVol);
                        channel7Sound.setText(String.valueOf(soundConfig.soundId));
                        channel7Cue.setText(soundConfig.cueName);
                        if (soundConfig.transitions.length != 0)
                            setTransitionText(soundConfig, channel7PredeterTransVol, channel7PredeterTransTime);
                        break;
                    case 7:
                        channel8Slider.setValue(soundConfig.startVol);
                        channel8Sound.setText(String.valueOf(soundConfig.soundId));
                        channel8Cue.setText(soundConfig.cueName);
                        if (soundConfig.transitions.length != 0)
                            setTransitionText(soundConfig, channel8PredeterTransVol, channel8PredeterTransTime);
                        break;

                }
                break;
            }
        }

    }

    private void setTransitionText(MixerJsonObject.PlayScene.SoundConfiguration soundConfig, Label channel1PredeterTransVol, Label channel1PredeterTransTime) {
        if (soundConfig.transitions[0].transitionType.equals("FADEIN")) {
            if (soundConfig.transitions.length > 1){
                channel1PredeterTransVol.setText(String.valueOf(soundConfig.transitions[1].newVolume));
                channel1PredeterTransTime.setText(soundConfig.transitions[1].transitionTime + " ms");
            }
        } else {
            channel1PredeterTransVol.setText(String.valueOf(soundConfig.transitions[0].newVolume));
            channel1PredeterTransTime.setText(soundConfig.transitions[0].transitionTime + " ms");
        }
    }

    private void updateSceneList() throws IOException {
        sceneSelector.getItems().clear();
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

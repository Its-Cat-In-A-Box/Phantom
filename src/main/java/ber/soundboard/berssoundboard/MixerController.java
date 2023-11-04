package ber.soundboard.berssoundboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.application.Platform;
import javafx.css.converter.InsetsConverter;
import javafx.css.converter.PaintConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



import java.io.*;
import java.net.URL;
import java.util.*;

public class MixerController implements Initializable {
    File mixerDir;
    File mixerConfigFile;
    private static int sceneIdx = 0;
    @FXML
    private ScrollPane SoundListPane;
    @FXML
    private GridPane soundCueGridPane;
    @FXML
    private MenuButton sceneSelector;

    private static MixerJsonObject mixerJsonObject;

    @FXML
    void onMouseMovedInScrollPane(MouseEvent event) {
        if (event.getX() < 5){
            Platform.runLater(() -> SoundListPane.getScene().setCursor(Cursor.W_RESIZE));
        } else {
            Platform.runLater(() -> SoundListPane.getScene().setCursor(Cursor.DEFAULT));
        }
    }

    @FXML
    void onMouseLeftScrollPane(MouseEvent event) {
        Platform.runLater(() -> SoundListPane.getScene().setCursor(Cursor.DEFAULT));
    }

    @FXML
    void onMouseClickedScrollPane(MouseEvent event){

    }

    @FXML
    void onMouseDragScrollPane(MouseEvent event){

    }

    private void setScene(int scene){
        sceneIdx = scene;
        updateSoundCueList();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SoundListPane.setPrefWidth((double) (800 * 77) / 300);
        mixerDir = new File("data", MainApp.NAME);
        mixerConfigFile = new File(mixerDir, MainApp.NAME + ".json");
        //Todo test
        mixerDir = new File("data", MainApp.NAME);
        mixerConfigFile = new File("D:\\CodingFiles\\berssoundboard2.0\\build\\jpackage\\app\\data\\balls\\balls.json");
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(mixerConfigFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        mixerJsonObject = gson.fromJson(reader, MixerJsonObject.class);
        try {
            updateSceneList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!Objects.equals(sceneSelector.getItems().get(0).getText(), "+ Add new scene")){
            //If a scene is actually selected
            updateSoundCueList();
        }



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
            if (Objects.equals(playScene.sceneIdx, sceneIdx)){
                currentlySelectedScene = playScene;
            }
        }

        assert currentlySelectedScene != null;
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
        newSoundCueText.setOnMouseClicked(event -> addSoundToMixer(soundConfig));
        newSoundCueText.setPadding(new Insets(3, 0, 0, 3));
        return newSoundCueText;
    }


    private void addSoundToMixer(MixerJsonObject.PlayScene.SoundConfiguration soundConfig) {
        System.out.println("added sound to mixer");
    }

    private void updateSceneList() throws IOException {
        sceneSelector.getItems().clear();
        int i = mixerJsonObject.scenes.length;
        if (i > 0){ //If scene already exists
            for (int j = 0; j < i; j++){
                MenuItem newMenuItem = new MenuItem(mixerJsonObject.scenes[j].name);
                int finalJ = j;

                newMenuItem.setOnAction((event) -> {
                    setScene(finalJ);
                    sceneSelector.setText(newMenuItem.getText());
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
            Parent root = null;
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
        }));

        sceneSelector.getItems().add(addButtonMenuItem);
        sceneSelector.setText(sceneSelector.getItems().get(0).getText());
        updateJsonFile();
    }

    private void updateJsonFile() throws IOException {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        FileWriter writer = new FileWriter("D:\\CodingFiles\\berssoundboard2.0\\build\\jpackage\\app\\data\\balls\\balls.json");
        gson.toJson(mixerJsonObject, writer);
        writer.close();
    }

}

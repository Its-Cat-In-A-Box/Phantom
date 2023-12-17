package ber.soundboard.berssoundboard;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class SplashController implements Initializable {
    @FXML
    MediaView view;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media media = new Media(getClass().getResource("video/splash.mp4").toString());
        MediaPlayer mp = new MediaPlayer(media);
        mp.setAutoPlay(true);
        mp.setOnEndOfMedia(() -> {
            mp.seek(Duration.ZERO);
            mp.play();
        });
        view.setMediaPlayer(mp);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    MainApp.DOUT.writeUTF("LOADSOUND " + MainApp.NAME);
                    MainApp.DOUT.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                while (true){
                    try {
                        if (MainApp.DIN.available() > 0){
                            break;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                Platform.runLater(() -> {
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("mixer.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setFullScreen(true);
                    stage.show();
                    ((Stage) view.getScene().getWindow()).close();
                });
            }
        };
        new Timer().schedule(task, 1000);

    }
}

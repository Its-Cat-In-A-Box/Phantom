package ber.soundboard.berssoundboard;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class SplashController implements Initializable {
    @FXML
    MediaView view;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("im running fuck u");
        Media media = new Media(getClass().getResource("video/splash.mp4").toString());
        MediaPlayer mp = new MediaPlayer(media);
        mp.setAutoPlay(true);
        mp.setOnEndOfMedia(() -> {
            mp.seek(Duration.ZERO);
            mp.play();
        });
        view.setMediaPlayer(mp);

        Thread thread = new Thread(() -> {
            try {
                MainApp.DOUT.writeUTF("LOADSOUND " + MainApp.NAME);
                MainApp.DOUT.flush();
                while (true){
                    if (MainApp.DIN.available() > 0){
                        break;
                    }
                }

                Platform.runLater(() -> {
                    Parent root2 = null;
                    try {
                        root2 = FXMLLoader.load(getClass().getResource("mixer.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Stage stage2 = new Stage();
                    stage2.setScene(new Scene(root2));
                    stage2.setFullScreen(true);
                    stage2.show();
                    ((Stage) view.getScene().getWindow()).close();
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }
}

package ber.soundboard.berssoundboard;

import com.almasb.fxgl.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;


public class MainApp extends Application {
    static String NAME; //TODO remove test case
    public static HashMap<String, Object> GLOBAL_DATA = new HashMap<>();
    public static DataOutputStream DOUT;
    public static DataInputStream DIN;
    @Override
    public void start(Stage stage) throws Exception {
        MainApp.GLOBAL_DATA.put("HasNewData", "false");
        ProcessBuilder serverProc = new ProcessBuilder(System.getProperty("user.dir") + "/script/venv/Scripts/python", System.getProperty("user.dir") + "/script/main.py").inheritIO();
        serverProc.redirectErrorStream(true);

        serverProc.start();
        Thread.sleep(2000);
        Socket socket = new Socket("localhost", 1223);
        DOUT = new DataOutputStream(socket.getOutputStream());
        DIN = new DataInputStream(socket.getInputStream());
        String msg = DIN.readUTF();
        if (msg.equals("CLIENTCONNECTED")){
            DOUT.writeUTF("SERVERCONNECTED");
            DOUT.flush();
        } else {
            throw new Exception("Server issues different command than client!");
        }
        msg = DIN.readUTF();
        if (msg.equals("REQUEST_DATA_DIR")){
            DOUT.writeUTF(System.getProperty("user.dir") + "\\data");
            DOUT.flush();
        } else {
            throw new Exception("Server issues different command than client!");
        }

        Parent root = FXMLLoader.load(getClass().getResource("selectplay.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("mixer.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
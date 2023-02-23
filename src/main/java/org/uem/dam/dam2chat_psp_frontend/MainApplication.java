package org.uem.dam.dam2chat_psp_frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainApplication extends Application {
    public static final String host = "localhost";
    public static final int port = 2453;

    @Override
    public void start(Stage stage) throws IOException {
        startServerConnection(); // FIXME organize better init structure
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void startServerConnection() throws IOException {
        System.out.println("Trying to connect");
        Socket socket = new Socket(host, port);
        System.out.println(socket.isConnected());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF("Hello world from client!!");
    }
}
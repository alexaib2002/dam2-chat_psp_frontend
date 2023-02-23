package org.uem.dam.dam2chat_psp_frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.uem.dam.dam2chat_psp_frontend.utils.Notifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainApplication extends Application {
    public static final String host = "localhost";
    public static final int port = 2453;
    private Socket socket;
    private String nick;
    DataOutputStream out;

    @Override
    public void start(Stage stage) throws IOException {
        // FIXME prompt username
        nick = "Username1";
        // Base inits, needed for networking and GUI
        initMainWindow(stage);
        startServerConnection();
    }

    @Override
    public void stop() throws Exception {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            System.err.println("Error while closing the connection to the server");
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }

    private void initMainWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("ChatPSP");
        stage.setScene(scene);
        stage.show();
        ((MainController) fxmlLoader.getController()).setMsgWriter(this::sendComposedMsg);
    }

    private void startServerConnection() {
        System.out.println("Trying to connect");
        try {
            socket = new Socket(host, port);
            out = new DataOutputStream(socket.getOutputStream());
            sendMsg("[nick]" + nick);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream()); // Receive data from server
            String chatHistory = inputStream.readUTF();
            System.out.println(chatHistory);
        } catch (IOException e) {
            Notifier.createAlert(e.getMessage(), e.toString(), Alert.AlertType.ERROR).showAndWait();
            System.exit(5);
        }
    }

    private void sendComposedMsg(String msg) {
        String send = String.format("::%s:: %s", nick, msg);
        sendMsg(send);
    }

    private void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
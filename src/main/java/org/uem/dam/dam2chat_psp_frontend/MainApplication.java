package org.uem.dam.dam2chat_psp_frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
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
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private MainController mainController;

    @Override
    public void start(Stage stage) throws IOException {
        nick = promptNick();
        // Base inits, needed for networking and GUI
        initMainWindow(stage);
        startServerConnection();
        mainController.updateChat(inputStream.readUTF()); // Receive chat history
        ChatTask task = new ChatTask(inputStream, mainController::appendMsgChat);
        task.setOnFailed(e -> {
            Notifier.createAlert("Unexpected error",
                    "Abrupt end of client thread", Alert.AlertType.ERROR).showAndWait();
            System.exit(5);
        });
        new Thread(task).start();
    }

    private String promptNick() {
        TextInputDialog usernameAlert = new TextInputDialog();
        usernameAlert.setHeaderText("Your username");
        usernameAlert.setContentText("Please insert your username:");
        usernameAlert.showAndWait();
        return usernameAlert.getEditor().getText();
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
        stage.resizableProperty().set(false);
        stage.show();
        mainController =  fxmlLoader.getController();
        mainController.setMsgSocketSender(this::sendComposedMsg);
        mainController.nickTxt.setText(nick);
    }

    private void startServerConnection() {
        System.out.println("Trying to init server connection...");
        try {
            socket = new Socket(host, port);
            // Open data streams
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            sendMsg("[nick]" + nick); // Greet the server
        } catch (IOException e) {
            Notifier.createAlert(e.getMessage(), e.toString(), Alert.AlertType.ERROR).showAndWait();
            System.exit(5);
        }
        System.out.println("Connection successfully established");
    }

    private void sendComposedMsg(String msg) {
        String send = String.format("%s: %s", nick, msg);
        sendMsg(send);
    }

    private void sendMsg(String msg) {
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            Notifier.createAlert(e.getMessage(), e.toString(), Alert.AlertType.ERROR).showAndWait();
            System.exit(5);
        }
    }
}
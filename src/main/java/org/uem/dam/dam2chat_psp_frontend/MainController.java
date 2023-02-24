package org.uem.dam.dam2chat_psp_frontend;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class MainController {

    @FXML
    public TextField msgInputTxt;
    @FXML
    public TextArea chatTextArea;
    private Consumer<String> msgSocketSender;

    @FXML
    protected void onMsgSendButtonPressed() {
        msgSocketSender.accept(msgInputTxt.getText());
    }

    public void setMsgSocketSender(Consumer<String> msgSocketSender) {
        this.msgSocketSender = msgSocketSender;
    }

    public void updateChat(String chat) {
        chatTextArea.setText(chat);
    }

    public void appendMsgChat(String msg) {
        chatTextArea.appendText(msg + "\n");
    }
}
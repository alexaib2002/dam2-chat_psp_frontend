package org.uem.dam.dam2chat_psp_frontend;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class MainController {

    @FXML
    public TextField msgInputTxt;
    private Consumer<String> msgWriter;

    @FXML
    protected void onMsgSendButtonPressed() {
        msgWriter.accept(msgInputTxt.getText());
    }

    public void setMsgWriter(Consumer<String> msgWriter) {
        this.msgWriter = msgWriter;
    }
}
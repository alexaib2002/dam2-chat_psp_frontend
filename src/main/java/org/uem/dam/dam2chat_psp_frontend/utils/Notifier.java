package org.uem.dam.dam2chat_psp_frontend.utils;

import javafx.scene.control.Alert;

public class Notifier {
    public static Alert createAlert(String title, String content, Alert.AlertType type) {
        Alert dialog = new Alert(type);
        dialog.setTitle("ChatPSP");
        dialog.setHeaderText(title);
        dialog.setContentText(content);
        return dialog;
    }
}

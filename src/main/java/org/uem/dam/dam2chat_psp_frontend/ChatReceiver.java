package org.uem.dam.dam2chat_psp_frontend;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.function.Consumer;

public class ChatReceiver extends Thread {
    private DataInputStream inputStream;
    private Consumer<String> msgTextAppender;

    public ChatReceiver(DataInputStream inputStream, Consumer<String> msgTextAppender) {
        this.inputStream = inputStream;
        this.msgTextAppender = msgTextAppender;
    }

    @Override
    public void run() {
        try {
            while (true) msgTextAppender.accept(inputStream.readUTF());
        } catch (IOException e) {
            System.err.println("Lost server connection");
        }
    }
}

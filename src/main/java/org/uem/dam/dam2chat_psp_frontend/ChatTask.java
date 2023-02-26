package org.uem.dam.dam2chat_psp_frontend;

import javafx.concurrent.Task;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.function.Consumer;

public class ChatTask extends Task<Object> {
    private DataInputStream inputStream;
    private Consumer<String> msgTextAppender;

    public ChatTask(DataInputStream inputStream, Consumer<String> msgTextAppender) {
        this.inputStream = inputStream;
        this.msgTextAppender = msgTextAppender;
    }

    @Override
    protected Object call() throws IOException {
        while (true) msgTextAppender.accept(inputStream.readUTF());
    }
}

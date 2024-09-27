package com.example.aes_chat.program.connection;

import java.io.FileNotFoundException;

public interface MessageListener {
    public void onMessage(String messageType, String fromLogin,String login, String msgBody) throws FileNotFoundException;
}

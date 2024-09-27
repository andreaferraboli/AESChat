package com.example.aes_chat.program.connection;

public interface UserStatusListener {
    public void online(String login);
    public void offline(String login);
}

package com.example.aes_chat.program.connection;

import java.util.ArrayList;


public class ServerMain {
    public static ArrayList<String> users=new ArrayList<>();
    public static void main(String args[]) {

        int port = 1111;
        Server server = new Server(port);
        server.start();

    }

}

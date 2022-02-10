package program.connection;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import program.scene.MainMenu;
import program.scene.MainProgram;
import program.scene.SecondWindow;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatClient extends Thread{
    private final String serverName;
    private final int serverPort;
    private Socket socket;
    private static  OutputStream serverOut;
    private InputStream serverIn;
    private BufferedReader bufferIn;
    public static String name;
    public static String password;

    private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();
    private static ChatClient client;

    public ChatClient(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }



    public void run() {
        client.addUserStatusListener(new UserStatusListener() {
            @Override
            public void online(String login) {
                System.out.println("ONLINE: " + login);
                MainMenu.utentiOnline.add(login);
            }

            @Override
            public void offline(String login) {
                System.out.println("OFFLINE: " + login);
            }
        });
        client.addMessageListeners(new MessageListener() {
            @Override
            public void onMessage(String messageType, String fromLogin, String login, String msgBody) throws FileNotFoundException {
                System.out.println(fromLogin+ " You got a message from " + login + ": " + msgBody);
                msgBody = AES.decrypt(msgBody, AES.sK);
                if(fromLogin.contains("#")){
                    fromLogin=fromLogin.replace("#","group_" );
                    SecondWindow.refresh=fromLogin;
                }
                else{
                   SecondWindow.refresh=login;
                }
                if (messageType.equals("msg"))
                    SecondWindow.showmessage(fromLogin,login,msgBody);
                else
                    SecondWindow.showmessagefile(fromLogin,login,msgBody);

            }
        });

        if (!client.connect()) {
            System.err.println("Connection failed.");
        }
        else {
            System.out.println("Connection successful!");
            Scanner sc = new Scanner(System.in);
            boolean loginAcc = false;
            do {

                name = MainProgram.t1.getText() ;
                String password = MainProgram.t2.getText();
                try {
                    if (client.login(name, password)) {
                        loginAcc = false;
                        System.out.println("Login successful!");
                        ServerMain.users.add(name);

                    } else {
                        loginAcc = true;
                        System.out.println("Login failed!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }while(loginAcc);

            //client.logoff();
        }
    }
    public static void sendmessage(String msg, String sendTo) throws IOException {

            msg = AES.encrypt(msg, AES.sK);
            client.msg(sendTo, name, msg);

    }

    public static byte[] method(File file) throws IOException
    {

        // Creating an object of FileInputStream to
        // read from a file
        FileInputStream fl = new FileInputStream(file);

        // Now creating byte array of same length as file
        byte[] arr = new byte[(int)file.length()];

        // Reading file content to byte array
        // using standard read() method
        fl.read(arr);

        // lastly closing an instance of file input stream
        // to avoid memory leakage
        fl.close();

        // Returning above byte array
        return arr;
    }

    public static void sendmessagefile(File msg, String sendTo) throws IOException {

        JSONObject jsonObject = new JSONObject();
        String fileName = msg.getName();
        byte[] array = Files.readAllBytes(msg.toPath());
        String message = new String(array);
        jsonObject.put("FileName", fileName);
        jsonObject.put("File", message);
        message = jsonObject.toString();
        message = AES.encrypt(message, AES.sK);
        client.msgfile(sendTo, name, message);


    }



    public static void sendmessageGroup(String msg, String topic) throws IOException {
        topic = "#" + topic;
        msg = AES.encrypt(msg, AES.sK);
        client.msg(topic,  msg);
    }

    private void msg(String topic, String msgBody) throws IOException {
        String cmd = "msg " + topic + " " + name +  " " + msgBody + "\n";
        serverOut.write(cmd.getBytes());
    }

    private void msg(String sendTo, String me, String msgBody) throws IOException {
        String cmd = "msg " + sendTo + " " + me +  " " + msgBody + "\n";
        serverOut.write(cmd.getBytes());
    }

    private void msgfile(String sendTo, String me, String msgBody) throws IOException {
        String cmd = "msgfile " + sendTo + " " + me +  " " + msgBody + "\n";
        serverOut.write(cmd.getBytes());
    }

    private void logoff() throws IOException {
        String cmd = "logoff\n";
        serverOut.write(cmd.getBytes());
    }

    private boolean login(String login, String password) throws IOException {
        String cmd = "login " + login + " " + password + "\n";
        serverOut.write(cmd.getBytes());

        String response = bufferIn.readLine();
        //System.out.println("Response: " + response);

        if ("login done".equals(response)) {

            startMessageReader();
            return true;
        }
        else {
            return false;
        }

    }

    private void startMessageReader() {
        Thread t = new Thread() {
            @Override
            public void run() {

                readMessageLoop();
            }
        };t.start();
    }

    private void readMessageLoop() {
        try {
            String line;

            while (((line = bufferIn.readLine() ) != null)) {

                String [] tokens = StringUtils.split(line);
                if (tokens != null && tokens.length > 0) {
                    String cmd = tokens[0];
                    if ("online".equals(cmd)) {
                        handleOnline(tokens);
                    }
                    else if ("offline".equals(cmd)) {
                        handleOffline(tokens);
                    }
                    else if ("msg".equals(cmd)) {
                        String tokensMsg [] = StringUtils.split(line, null, 4);
                        handleMessage(tokensMsg);
                    }
                    else if ("msgfile".equals(cmd)) {
                        String tokensMsg [] = StringUtils.split(line, null, 4);
                        handleMessage(tokensMsg);
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }


    }


    private void handleMessage(String[] tokens) throws FileNotFoundException {
        String messageType = tokens[0];
        String login = tokens[1];
        String loginFrom = tokens[2];
        String msgBody = tokens[3];
        //System.out.println(login + ": " + msgBody);

        MainMenu.NewChat1(loginFrom);

        for ( MessageListener listener: messageListeners) {

            listener.onMessage(messageType, login, loginFrom, msgBody);
        }
    }
    public static void GroupCreation(String str) throws IOException {
        System.out.println("groupcreatione.string="+str);
        serverOut.write(str.getBytes());
    }

    private void handleOffline(String[] tokens) {
        String login = tokens[1];
        for (UserStatusListener listener : userStatusListeners) {
            listener.offline(login);
        }
    }

    private void handleOnline(String[] tokens) {
        String login = tokens[1];
        for (UserStatusListener listener : userStatusListeners) {
            listener.online(login);
        }

    }

    private boolean connect() {
        try {
            this.socket = new Socket(serverName, serverPort);
            System.out.println("Client port is " + socket.getLocalPort());
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.bufferIn = new BufferedReader(new InputStreamReader(serverIn));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUserStatusListener(UserStatusListener listener) {
        userStatusListeners.add(listener);
    }
    public void removeUserStatusListener(UserStatusListener listener) {
        userStatusListeners.remove(listener);
    }
    public void addMessageListeners(MessageListener listener) {
        messageListeners.add(listener);
    }
    public void removeMessageListeners(MessageListener listener) {
        messageListeners.remove(listener);
    }

    public void setClient(ChatClient client) {
        this.client=client;
    }
}

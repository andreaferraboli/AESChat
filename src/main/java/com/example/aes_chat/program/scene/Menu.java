package com.example.aes_chat.program.scene;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import com.example.aes_chat.program.connection.ChatClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Menu extends Application {

    GridPane barrasopra=new GridPane();
    public static GridPane menuprincipale = new GridPane();
    public static Window owner;
    public static ArrayList<String> utentiOnline=new ArrayList<>();
    public static final String pathname = "src/main/resources/com/example/aes_chat/filechat";


    @Override
    public void start(Stage stage) throws Exception {
        //parte il client
        menuprincipale.getChildren().clear();
        Counter.counter++;

        //creo un client una sola volta
        if (Counter.counter == 1) {
            ChatClient client = new ChatClient("localhost", 1111);
            client.setClient(client);
            client.start();
        }

        Scene scena1;
        GridPane root = new GridPane();
        root.setId("Pane");
        root.add(barrasopra,0,1);
        ScrollPane s7=new ScrollPane();
        s7.setContent(menuprincipale);
        s7.setMaxSize(300,200);
        s7.setPrefSize(300,200);
        menuprincipale.setAlignment(Pos.CENTER);
        root.add(s7,0,2);

        scena1 = new Scene(root);

        scena1.getStylesheets().add(getClass().getResource(MainProgram.filestyle).toExternalForm());


        //scena1 layout
        List<String> userchat = new ArrayList<String>();

        final File folder = new File(pathname);
        //riempio un arraylist con i nomi delle chat
        listNames(folder, userchat);

        Button cerca = new Button("cerca");
        cerca.setId("cerca");
        cerca.setOnAction(e -> {
            Scenecerca s4=new Scenecerca();
            try {
                s4.start(stage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        Button refresh = new Button("aggiorna");
            refresh.setId("refresh");
        refresh.setOnAction(e -> {
            Menu s1=new Menu();
            try {
                s1.start(stage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        });
        Button newchat = new Button("new chat");
        newchat.setId("nuovachat");

        newchat.setOnAction(e -> {
            ScenenewChat s3=new ScenenewChat();
            try {
                s3.start(stage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
        Button file= new Button("inserisci file dal pc");
        file.setId("cerca");
        file.setOnAction(e -> {
            SceneFile s5=new SceneFile();
            try {
                s5.start(stage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        Label vuoto=new Label("");
        vuoto.setPrefWidth(50);
        Button newgroup = new Button("new group");
        newgroup.setId("nuovogruppo");

        newgroup.setOnAction(e -> {
            ScenenewGroup s5=new ScenenewGroup();
            try {
                s5.start(stage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });


        //aggiunta dei bottoni al gridpane
        barrasopra.add(newgroup,3,0);
        barrasopra.add(cerca, 0, 0);
        barrasopra.add(newchat, 2, 0);
        barrasopra.add(file, 5, 0);
        barrasopra.add(refresh, 6, 0);
        barrasopra.setPadding(new Insets(10, 10, 10, 10));
        owner=newchat.getScene().getWindow();



        //stampa di tutte le chat nel menù principale

        for (int n = 0; n < userchat.size(); n++) {
            String testoButton = userchat.get(n);

            Button temp = new Button(testoButton);
            if(temp.getText().contains("group_")){
                temp.setText(testoButton.replaceFirst("group_", ""));
                temp.setId("buttonMenuGroup");
            }
            else{
                temp.setId("buttonMenu");
            }
            temp.setPrefSize(270, 50);

            temp.setOnAction(e -> {
                if(temp.getId().equals("buttonMenuGroup")) {
                    SecondWindow secondWindow = new SecondWindow("group_"+temp.getText());
                    try {
                        SecondWindow.nomechat ="group_"+temp.getText();
                        secondWindow.start(stage);

                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }
                else{
                    SecondWindow secondWindow = new SecondWindow(temp.getText());
                    try {
                        SecondWindow.nomechat = temp.getText();
                        secondWindow.start(stage);

                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }
            });
            menuprincipale.add(temp,0,n);
            menuprincipale.setPadding(new Insets(10, 10, 10, 10));

        }

        stage.setScene(scena1);
        stage.centerOnScreen();
        stage.setTitle("discord");
        stage.show();

    }

    private void listNames(File folder, List<String> userchat) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            userchat.add(fileEntry.getName().replaceAll(".txt", ""));
            //System.out.println(fileEntry.getName());
        }
    }

    //metodo per creare una nuova chat
    public static void NewChat(String nomefile) {

        String username = nomefile;
        String path = pathname+"\\" + username + ".txt";


        try {
            File file = new File(path);

            if (file.exists())
                alert.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        "hai già creato una chat con questo nome");
            else if (file.createNewFile())
                alert.showAlert(Alert.AlertType.INFORMATION, owner, "Form information!",
                        "nuova chat creata");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //metodo per far apparire la notifica di un messaggio da un utente o gruppo nuovo
    public static void NewChat1(String nomefile) {
        String username = nomefile;
        String path = pathname+"\\" + username + ".txt";


        try {
            File file = new File(path);


            if (file.exists()){}
            else{
                file.createNewFile();
                Platform.runLater(()->{
                    Button temp = new Button();
                    if(username.contains("group_")){
                        temp.setText("nuovo gruppo "+username.replaceFirst("group_", "")+",aggiorna");
                    }
                    else{
                        temp.setText("nuova chat con "+username+",aggiorna");
                    }
                    temp.setPrefSize(270, 50);
                    temp.setId("refreshmenu");
                    menuprincipale.add(temp,0,menuprincipale.getRowCount()+1);
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

package com.example.aes_chat.program.scene;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.example.aes_chat.program.connection.ChatClient;

import java.io.File;
import java.io.IOException;

public class ScenenewGroup extends Application {

@FXML
private TextField nuovogruppo1;
    @FXML
    private TextField nuovogruppo11;

    public void start(Stage stage) throws IOException {

            GridPane g1 = FXMLLoader.load(getClass().getResource("Scenanewgroup.fxml"));
            GridPane root=new GridPane();
            Button indietro = new Button("<-");

            indietro.setId("indietro");
            indietro.setOnAction(e -> {
                //s1.interrupt();
                Menu prymarystage = new Menu();
                try {
                    prymarystage.start(stage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

            root.add(indietro,0,0);

            root.add(g1,0,2);

            Scene scene = new Scene(root);
            stage.setTitle("New Group");


            scene.getStylesheets().add(getClass().getResource(MainProgram.filestyle).toExternalForm());
            stage.centerOnScreen();
            stage.setScene(scene);
            }
            @FXML
            public void creagruppo() throws IOException {
                String nomegruppo ="group_"+ nuovogruppo1.getText();

                String path = Menu.pathname+"\\" + nomegruppo + ".txt";
                nomegruppo="join #"+nuovogruppo1.getText();
                System.out.println("nomegruppo="+nomegruppo);
                System.out.println("nuovogruppo.gettext="+nuovogruppo1.getText());
                ChatClient.GroupCreation(nomegruppo);
                ChatClient.sendmessageGroup("",nuovogruppo1.getText());
                try {
                    File file = new File(path);

                    if (file.exists())
                        alert.showAlert(Alert.AlertType.ERROR, Menu.owner, "Form Error!",
                                "hai già creato una chat con questo nome");
                    else if (file.createNewFile())
                        alert.showAlert(Alert.AlertType.INFORMATION, Menu.owner, "Form information!",
                                "nuova chat creata");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @FXML
            public void entragruppo() throws IOException {
                String nomegruppo ="group_"+ nuovogruppo11.getText();

                String path = Menu.pathname+"\\" + nomegruppo + ".txt";
                nomegruppo="join #"+nuovogruppo11.getText();
                ChatClient.GroupCreation(nomegruppo);
                ChatClient.sendmessageGroup("",nuovogruppo11.getText());
                try {
                    File file = new File(path);

                    if (file.exists())
                        alert.showAlert(Alert.AlertType.ERROR, Menu.owner, "Form Error!",
                                "hai già creato una chat con questo nome");
                    else if (file.createNewFile())
                        alert.showAlert(Alert.AlertType.INFORMATION, Menu.owner, "Form information!",
                                "unito al gruppo:"+nuovogruppo11.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }


    }


}


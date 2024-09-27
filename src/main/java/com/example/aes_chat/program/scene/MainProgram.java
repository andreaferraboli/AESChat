package com.example.aes_chat.program.scene;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;

public class MainProgram extends Application{
public static PasswordField t2;
public static TextField t1;
public static String filestyle = "/com/example/aes_chat/style.css";


    public void start(Stage stage) throws IOException {

            Pane root =new Pane();
            root.setId("Pane");
            t1=new TextField();
            t1.setPrefSize(321,31.0);
            t1.setLayoutX(243);
            t1.setLayoutY(82);
            t2=new PasswordField();
            t2.setPrefSize(321,31.0);
            t2.setLayoutX(243);
            t2.setLayoutY(185);
            Label l2=new Label("nome:");
            l2.setId("text");
            l2.setPrefSize(90,31.0);
        l2.setLayoutX(153);
        l2.setLayoutY(82);
        l2.setAlignment(Pos.CENTER);
        l2.setFont(new Font("Arial",23));
        Label l1=new Label("password:");
        l1.setId("text");
        l1.setPrefSize(120,31.0);
            l1.setLayoutX(123);
            l1.setLayoutY(185);
            l1.setAlignment(Pos.CENTER);
            l1.setFont(new Font("Arial",23));

            Button invio=new Button("entra nel server");
            invio.setOnAction(e->{
                if(t1.getText().equals("")||t2.getText().equals(""))
                {
                    Window owner=t1.getScene().getWindow();
                    alert.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                            "completa tutti i campi richiesti");
                }
                else{
                        Menu prymarystage = new Menu();
                    try {
                        prymarystage.start(stage);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
            });

            invio.setId("invio");
            invio.setLayoutX(243);
            invio.setLayoutY(268);
            invio.setPrefSize(114,59);
            root.getChildren().addAll(invio,t1,t2,l1,l2);
            Scene scene = new Scene(root,600,400);
            stage.setTitle("enter in the server");

        String cssFile = Objects.requireNonNull(getClass().getResource(filestyle)).toExternalForm();
        if (cssFile != null) {
            scene.getStylesheets().add(cssFile);
        } else {
            System.err.println("Could not find style.css");
        }
            stage.centerOnScreen();
            stage.setScene(scene);
            stage.show();
        }
    public static void main(String[] args) {
        launch();
    }
}




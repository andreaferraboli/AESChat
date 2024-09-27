package com.example.aes_chat.program.scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneFile extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        SceneFile.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(SceneFile.class.getResource("file.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800  , 600);
        stage.setScene(scene);
        stage.setTitle("FileDecripter");
        stage.show();
    }
}


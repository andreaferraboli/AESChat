package program.scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static program.scene.SecondWindow.listFiles;

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


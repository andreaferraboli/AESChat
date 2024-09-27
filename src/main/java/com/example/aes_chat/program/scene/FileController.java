package com.example.aes_chat.program.scene;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import com.example.aes_chat.program.connection.AES;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileController
{
    final String secretKey = "ssshhhhhhhhhhh!!!!";

    public TextArea plainText;
    public TextArea cryptText;

    public void onSelectFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File f = fileChooser.showOpenDialog(SceneFile.stage);

        if (f == null)
            return;
        try {
            Scanner r = new Scanner(f);
            StringBuilder txt = new StringBuilder();
            StringBuilder txtCrypt = new StringBuilder();
            while (r.hasNextLine()) {
                String s = r.nextLine();
                txt.append(s);
                txt.append("\n");
            }
            r.close();
            String txtPlain = txt.toString();
            plainText.setText(txtPlain);
            cryptText.setText(AES.encrypt(txtPlain,secretKey));
        } catch ( IOException e) {
            new Alert(Alert.AlertType.ERROR, "Errore IO: " + e.getMessage(), ButtonType.OK).show();
            e.printStackTrace();
        }
    }

    public void onClearPlain(ActionEvent actionEvent) {
        plainText.clear();
    }
    public void Back(ActionEvent actionEvent) {
    }

    public void onClearCrypt(ActionEvent actionEvent) {
        cryptText.clear();
    }

    public void unUncrypt(ActionEvent actionEvent) {
        plainText.setText(AES.decrypt(cryptText.getText(),secretKey));
    }

    public void onBtAction(ActionEvent actionEvent) {

    }
}
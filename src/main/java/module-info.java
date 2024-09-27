module com.example.aes_chat {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;
    requires org.json;

    // If your FXML controllers are in 'com.example.aes_chat.program.scene', open that specific package
    opens com.example.aes_chat.program.scene to javafx.fxml;

    exports com.example.aes_chat.program.scene to javafx.graphics;
}

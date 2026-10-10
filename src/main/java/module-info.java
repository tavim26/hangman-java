module com.example.hangman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires java.sql;

    // Deschide pachetul com.example.hangman pentru javafx.fxml
    opens com.example.hangman to javafx.fxml;

    // Deschide pachetul GUI pentru javafx.fxml
    opens GUI to javafx.fxml;
    opens Model to javafx.base;

    // Exportă pachetele com.example.hangman și GUI
    exports com.example.hangman;
    exports GUI;
}

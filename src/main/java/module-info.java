module com.example.hangman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens GUI to javafx.fxml;
    opens Model to javafx.base;

    exports GUI;
}
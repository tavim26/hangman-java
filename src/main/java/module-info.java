module io.github.tavim26.hangman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens io.github.tavim26.hangman.controller to javafx.fxml;
    opens io.github.tavim26.hangman.model to javafx.base;

    exports io.github.tavim26.hangman;
}
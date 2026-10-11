package io.github.tavim26.hangman.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;

/** Small helpers for the dialogs shown by the controllers. */
final class Alerts
{
    private Alerts()
    {
    }

    static void info(String title, String message)
    {
        show(Alert.AlertType.INFORMATION, title, message);
    }

    static void error(String title, String message)
    {
        show(Alert.AlertType.ERROR, title, message);
    }

    static void databaseError(SQLException e)
    {
        e.printStackTrace();
        error("Database error", "Could not reach the database. Please check that MySQL is running and try again.");
    }

    /** Shows an OK/Cancel dialog and returns true if the user pressed OK. */
    static boolean confirm(String title, String header, String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        return alert.showAndWait().filter(button -> button == ButtonType.OK).isPresent();
    }

    private static void show(Alert.AlertType type, String title, String message)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
package io.github.tavim26.hangman.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** Switches between the application's screens, all shown in the same window. */
public final class SceneManager
{
    public enum View
    {
        LOGIN("/fxml/login.fxml", "Log In/Sign Up", 302, 505),
        DASHBOARD("/fxml/dashboard.fxml", "Player Dashboard", 632, 435),
        GAME("/fxml/game.fxml", "Hangman", 404, 489);

        private final String fxmlPath;
        private final String title;
        private final int width;
        private final int height;

        View(String fxmlPath, String title, int width, int height)
        {
            this.fxmlPath = fxmlPath;
            this.title = title;
            this.width = width;
            this.height = height;
        }
    }

    private static Stage stage;

    private SceneManager()
    {
    }

    public static void init(Stage primaryStage)
    {
        stage = primaryStage;
        stage.setResizable(false);
    }

    /** Shows the given screen and returns its controller. */
    public static <T> T show(View view) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(view.fxmlPath));
        Parent root = loader.load();

        stage.setScene(new Scene(root, view.width, view.height));
        stage.setTitle(view.title);
        stage.show();

        return loader.getController();
    }
}
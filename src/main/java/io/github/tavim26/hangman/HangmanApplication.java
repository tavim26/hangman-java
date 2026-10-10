package io.github.tavim26.hangman;

import io.github.tavim26.hangman.controller.SceneManager;
import io.github.tavim26.hangman.controller.SceneManager.View;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HangmanApplication extends Application
{
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        SceneManager.init(primaryStage);
        SceneManager.show(View.LOGIN);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
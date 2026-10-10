package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class GameView extends Application {


    public void start(Stage primaryStage) throws IOException
    {
        SceneManager.setStage(primaryStage);
        primaryStage.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource("/SceneBuilder/game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 404, 489);
        primaryStage.setTitle("Hangman");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

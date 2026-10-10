package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerView extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        SceneManager.setStage(primaryStage);
        primaryStage.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource("/SceneBuilder/player.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 632, 435);
        primaryStage.setTitle("Player Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
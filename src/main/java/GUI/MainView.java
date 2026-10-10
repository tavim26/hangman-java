package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        SceneManager.setStage(primaryStage);
        primaryStage.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource("/SceneBuilder/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 302, 505);
        primaryStage.setTitle("Log In/Sign Up");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
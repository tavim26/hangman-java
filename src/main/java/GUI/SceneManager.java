package GUI;

import Model.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage stage;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void changeScene(String fxml, double width, double height) throws IOException {
        Parent pane = FXMLLoader.load(SceneManager.class.getResource(fxml));
        stage.getScene().setRoot(pane);
        stage.setWidth(width);
        stage.setHeight(height);
    }

    public static void changeSceneWithUserData(String fxmlFile, int width, int height, Player player) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlFile));
        Parent root = loader.load();

        PlayerController controller = loader.getController();

        controller.setCurrentPlayer(player);

        stage.setScene(new Scene(root, width, height));
        stage.show();
    }



}

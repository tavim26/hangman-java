package GUI;

import BusinessLogic.GameLogic;
import Model.Game;
import Model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {

    @FXML
    private Text templateField;
    @FXML
    private Text domainField;
    @FXML
    private Text triesField;
    @FXML
    private Text lettersField;
    @FXML
    private TextField letterField;

    private GameLogic gameLogic;
    private Player currentPlayer;


    public void initialize(String difficulty, String category, Player player)
    {
        this.currentPlayer = player;
        gameLogic = new GameLogic();

        gameLogic.startGame(category, difficulty,currentPlayer);
        updateGameUI();
    }

    private void updateGameUI()
    {
        templateField.setText(gameLogic.getWordTemplate());
        domainField.setText(gameLogic.getCategory());
        triesField.setText(String.valueOf(gameLogic.getRemainingTries()));
        lettersField.setText(gameLogic.getGuessedLetters().toString());
    }

    @FXML
    private void clickOkButton() throws IOException {
        String input = letterField.getText().toLowerCase();
        letterField.clear();

        if (input.length() == 1 && Character.isLetter(input.charAt(0)))
        {
            if (gameLogic.guessLetter(input))
            {
                updateGameUI();
            }
        }

        checkGameStatus();
    }

    private void checkGameStatus() throws IOException {
        if (gameLogic.isGameOver())
        {

            if (gameLogic.getRemainingTries() <= 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText("You've run out of tries!");
                alert.setContentText("The correct word was: " + gameLogic.getCurrentWord());

                alert.showAndWait();

                SceneManager.changeSceneWithUserData("/SceneBuilder/player.fxml",632,435,currentPlayer);
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Congratulations!");
                alert.setHeaderText("You guessed the word!");
                alert.setContentText("The word was: " + gameLogic.getCurrentWord());

                alert.showAndWait();

                SceneManager.changeSceneWithUserData("/SceneBuilder/player.fxml",632,435,currentPlayer);
            }
        }
    }



}

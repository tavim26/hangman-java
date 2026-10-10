package io.github.tavim26.hangman.controller;

import io.github.tavim26.hangman.controller.SceneManager.View;
import io.github.tavim26.hangman.model.Category;
import io.github.tavim26.hangman.model.Difficulty;
import io.github.tavim26.hangman.model.HangmanGame;
import io.github.tavim26.hangman.model.Player;
import io.github.tavim26.hangman.service.GameService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class GameController
{
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

    private final GameService gameService = new GameService();
    private Player player;
    private HangmanGame game;

    public void startGame(Player player, Difficulty difficulty, Category category)
    {
        this.player = player;
        this.game = gameService.startGame(difficulty, category);
        updateGameUI();
    }

    @FXML
    private void clickOkButton() throws IOException
    {
        String input = letterField.getText().trim();
        letterField.clear();

        if (input.length() == 1 && Character.isLetter(input.charAt(0)))
        {
            game.guess(input.charAt(0));
            updateGameUI();
        }

        if (game.isOver())
        {
            finishGame();
        }
    }

    private void updateGameUI()
    {
        templateField.setText(game.getMaskedWord());
        domainField.setText(game.getCategory().getLabel());
        triesField.setText(String.valueOf(game.getRemainingTries()));
        lettersField.setText(game.getGuessedLetters().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
    }

    private void finishGame() throws IOException
    {
        try
        {
            gameService.saveResult(player, game);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        Alert alert;
        if (game.isWon())
        {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Congratulations!");
            alert.setHeaderText("You guessed the word!");
        }
        else
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("You've run out of tries!");
        }
        alert.setContentText("The word was: " + game.getWord());
        alert.showAndWait();

        DashboardController dashboard = SceneManager.show(View.DASHBOARD);
        dashboard.setPlayer(player);
    }
}
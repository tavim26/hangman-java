package io.github.tavim26.hangman.controller;

import io.github.tavim26.hangman.controller.SceneManager.View;
import io.github.tavim26.hangman.model.Category;
import io.github.tavim26.hangman.model.Difficulty;
import io.github.tavim26.hangman.model.HangmanGame;
import io.github.tavim26.hangman.model.Player;
import io.github.tavim26.hangman.service.GameService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML
    private Button okButton;

    private final GameService gameService = new GameService();
    private Player player;
    private HangmanGame game;

    @FXML
    private void initialize()
    {
        // Pressing Enter in the letter field works like clicking OK
        letterField.setOnAction(event -> okButton.fire());
    }

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
            Alerts.databaseError(e);
        }

        if (game.isWon())
        {
            Alerts.info("Congratulations!", "You guessed the word: " + game.getWord());
        }
        else
        {
            Alerts.info("Game over", "You've run out of tries! The word was: " + game.getWord());
        }

        DashboardController dashboard = SceneManager.show(View.DASHBOARD);
        dashboard.setPlayer(player);
    }
}
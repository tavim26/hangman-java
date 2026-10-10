package io.github.tavim26.hangman.controller;

import io.github.tavim26.hangman.controller.SceneManager.View;
import io.github.tavim26.hangman.model.Category;
import io.github.tavim26.hangman.model.Difficulty;
import io.github.tavim26.hangman.model.GameRecord;
import io.github.tavim26.hangman.model.Player;
import io.github.tavim26.hangman.service.GameService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DashboardController
{
    @FXML
    private AnchorPane playPane;
    @FXML
    private AnchorPane helpPane;
    @FXML
    private AnchorPane gamesPane;

    @FXML
    private Label usernameLabel;

    @FXML
    private RadioButton easyBox;
    @FXML
    private RadioButton mediumBox;
    @FXML
    private RadioButton hardBox;

    @FXML
    private RadioButton carsBox;
    @FXML
    private RadioButton plantsBox;
    @FXML
    private RadioButton animalsBox;
    @FXML
    private RadioButton countriesBox;
    @FXML
    private RadioButton jobsBox;
    @FXML
    private RadioButton gamesBox;

    @FXML
    private TableView<GameRecord> gamesTable;

    private final GameService gameService = new GameService();
    private Player currentPlayer;

    public void setPlayer(Player player)
    {
        this.currentPlayer = player;
        usernameLabel.setText(player.getUsername());
        populateGamesTable();
    }

    @FXML
    private void clickPlay()
    {
        showPane(playPane);
    }

    @FXML
    private void clickHelp()
    {
        showPane(helpPane);
    }

    @FXML
    private void clickGames()
    {
        showPane(gamesPane);
    }

    @FXML
    private void clickStartGame() throws IOException
    {
        Difficulty difficulty = getSelectedDifficulty();
        Category category = getSelectedCategory();

        if (difficulty == null || category == null)
        {
            showError("Please select both difficulty level and category.");
            return;
        }

        GameController gameController = SceneManager.show(View.GAME);
        gameController.startGame(currentPlayer, difficulty, category);
    }

    @FXML
    private void clickLogOut() throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Logout");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("You will be returned to the sign up screen.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            SceneManager.show(View.LOGIN);
        }
    }

    private void showPane(AnchorPane paneToShow)
    {
        playPane.setVisible(paneToShow == playPane);
        helpPane.setVisible(paneToShow == helpPane);
        gamesPane.setVisible(paneToShow == gamesPane);
    }

    private Difficulty getSelectedDifficulty()
    {
        if (easyBox.isSelected())
        {
            return Difficulty.EASY;
        }
        if (mediumBox.isSelected())
        {
            return Difficulty.MEDIUM;
        }
        if (hardBox.isSelected())
        {
            return Difficulty.HARD;
        }
        return null;
    }

    private Category getSelectedCategory()
    {
        if (carsBox.isSelected())
        {
            return Category.CARS;
        }
        if (plantsBox.isSelected())
        {
            return Category.PLANTS;
        }
        if (animalsBox.isSelected())
        {
            return Category.ANIMALS;
        }
        if (countriesBox.isSelected())
        {
            return Category.COUNTRIES;
        }
        if (jobsBox.isSelected())
        {
            return Category.JOBS;
        }
        if (gamesBox.isSelected())
        {
            return Category.VIDEO_GAMES;
        }
        return null;
    }

    private void populateGamesTable()
    {
        gamesTable.getColumns().clear();
        gamesTable.getItems().clear();

        gamesTable.getColumns().add(column("GameID", "gameID"));
        gamesTable.getColumns().add(column("PlayerID", "playerID"));
        gamesTable.getColumns().add(column("GameMode", "gameMode"));
        gamesTable.getColumns().add(column("Word", "word"));
        gamesTable.getColumns().add(column("RemainingAttempts", "remainingAttempts"));
        gamesTable.getColumns().add(column("Status", "status"));

        try
        {
            List<GameRecord> games = gameService.getHistory(currentPlayer);
            gamesTable.getItems().setAll(games);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static TableColumn<GameRecord, Object> column(String title, String property)
    {
        TableColumn<GameRecord, Object> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    private void showError(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
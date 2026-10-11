package io.github.tavim26.hangman.controller;

import io.github.tavim26.hangman.controller.SceneManager.View;
import io.github.tavim26.hangman.model.Category;
import io.github.tavim26.hangman.model.Difficulty;
import io.github.tavim26.hangman.model.GameRecord;
import io.github.tavim26.hangman.model.Player;
import io.github.tavim26.hangman.service.GameService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

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

    @FXML
    private void initialize()
    {
        gamesTable.getColumns().clear();
        gamesTable.getColumns().add(column("Difficulty", "difficulty"));
        gamesTable.getColumns().add(column("Category", "category"));
        gamesTable.getColumns().add(column("Word", "word"));
        gamesTable.getColumns().add(column("Tries left", "remainingAttempts"));
        gamesTable.getColumns().add(column("Result", "status"));
    }

    public void setPlayer(Player player)
    {
        this.currentPlayer = player;
        usernameLabel.setText(player.getUsername());
        loadGamesHistory();
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
            Alerts.error("Missing selection", "Please select both a difficulty level and a category.");
            return;
        }

        GameController gameController = SceneManager.show(View.GAME);
        gameController.startGame(currentPlayer, difficulty, category);
    }

    @FXML
    private void clickLogOut() throws IOException
    {
        if (Alerts.confirm("Confirm Logout", "Are you sure you want to log out?",
                "You will be returned to the sign up screen."))
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

    private void loadGamesHistory()
    {
        try
        {
            gamesTable.getItems().setAll(gameService.getHistory(currentPlayer));
        }
        catch (SQLException e)
        {
            Alerts.databaseError(e);
        }
    }

    private static TableColumn<GameRecord, Object> column(String title, String property)
    {
        TableColumn<GameRecord, Object> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }
}
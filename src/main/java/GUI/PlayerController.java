package GUI;

import Database.GameDAO;
import Model.Game;
import Model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class PlayerController {


    @FXML
    private StackPane rightPane;

    @FXML
    private AnchorPane playPane;

    @FXML
    private AnchorPane helpPane;


    @FXML
    private AnchorPane gamesPane;

    @FXML
    private Button playButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button trophiesButton;

    @FXML
    private Button gamesButton;

    @FXML
    private Button startGameButton;

    @FXML
    private Button logOutButton;

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
    private TableView<Game> gamesTable;


    Player currentPlayer;

    GameDAO gameDAO;

    public void setCurrentPlayer(Player player)
    {
        this.currentPlayer = player;
        usernameLabel.setText(player.getUsername());
        populateGamesTable();
    }

    @FXML
    private void clickPlay() {

        playPane.setVisible(true);

        helpPane.setVisible(false);
        gamesPane.setVisible(false);
    }

    @FXML
    private void clickHelp() {

        playPane.setVisible(false);

        helpPane.setVisible(true);
        gamesPane.setVisible(false);
    }



    @FXML
    private void clickGames() {
        playPane.setVisible(false);

        helpPane.setVisible(false);
        gamesPane.setVisible(true);
    }

    @FXML
    private void clickStartGame()
    {
        String selectedDifficulty = getSelectedDifficulty();
        String selectedCategory = getSelectedCategory();

        if (selectedDifficulty == null || selectedCategory == null)
        {
            showError("Please select both difficulty level and category.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneBuilder/game.fxml"));
            Parent root = loader.load();

            GameController gameController = loader.getController();

            gameController.initialize(selectedDifficulty, selectedCategory, currentPlayer);

            Scene scene = new Scene(root, 404, 489);
            Stage stage = (Stage) startGameButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
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
           SceneManager.changeScene("/SceneBuilder/main.fxml",302,505);
        }
        else
        {
            alert.close();
        }
    }



    private String getSelectedDifficulty()
    {
        if (easyBox.isSelected())
        {
            return "easy";
        }
        else if (mediumBox.isSelected())
        {
            return "medium";
        }
        else if (hardBox.isSelected())
        {
            return "hard";
        }
        return null;
    }

    private String getSelectedCategory()
    {
        if (carsBox.isSelected())
        {
            return "cars";
        }
        else if (plantsBox.isSelected())
        {
            return "plants";
        }
        else if (animalsBox.isSelected())
        {
            return "animals";
        }
        else if (countriesBox.isSelected())
        {
            return "countries";
        }
        else if (jobsBox.isSelected())
        {
            return "jobs";
        }
        else if (gamesBox.isSelected())
        {
            return "videogames";
        }

        return null;
    }




    private void populateGamesTable()
    {
        gamesTable.getColumns().clear();
        gamesTable.getItems().clear();

        TableColumn<Game, Integer> gameIdColumn = new TableColumn<>("GameID");
        gameIdColumn.setCellValueFactory(new PropertyValueFactory<>("gameID"));

        TableColumn<Game, Integer> playerIdColumn = new TableColumn<>("PlayerID");
        playerIdColumn.setCellValueFactory(new PropertyValueFactory<>("playerID"));

        TableColumn<Game, String> gameModeColumn = new TableColumn<>("GameMode");
        gameModeColumn.setCellValueFactory(new PropertyValueFactory<>("gameMode"));

        TableColumn<Game, String> wordColumn = new TableColumn<>("Word");
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));

        TableColumn<Game, Integer> remainingAttemptsColumn = new TableColumn<>("RemainingAttempts");
        remainingAttemptsColumn.setCellValueFactory(new PropertyValueFactory<>("remainingAttempts"));

        TableColumn<Game, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        gamesTable.getColumns().addAll(gameIdColumn, playerIdColumn, gameModeColumn, wordColumn, remainingAttemptsColumn, statusColumn);

        try {
            gameDAO = new GameDAO();
            List<Game> games = gameDAO.getGamesByPlayer(currentPlayer.getPlayerID());
            gamesTable.getItems().setAll(games);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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


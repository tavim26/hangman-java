package Database;

import Model.Game;
import Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDAO
{
    public void addGame(Game game) throws SQLException
    {
        String query = "INSERT INTO Game (PlayerID,GameMode, Word, RemainingAttempts, Status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, game.getPlayerID());
            stmt.setString(2,game.getGameMode());
            stmt.setString(3, game.getWord());
            stmt.setInt(4, game.getRemainingAttempts());
            stmt.setString(5, game.getStatus());
            stmt.executeUpdate();
        }
    }

    public Game getGameById(int gameID) throws SQLException
    {
        String query = "SELECT * FROM Game WHERE GameID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, gameID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Game(
                        rs.getInt("GameID"),
                        rs.getInt("PlayerID"),
                        rs.getString("Gamemode"),
                        rs.getString("Word"),
                        rs.getInt("RemainingAttempts"),
                        rs.getString("Status")
                );
            }
        }
        return null;
    }


    public List<Game> getGamesByPlayer(int playerID) throws SQLException {
        List<Game> gamesList = new ArrayList<>();
        String query = "SELECT * FROM Game WHERE PlayerID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Game game = new Game(
                        rs.getInt("GameID"),
                        rs.getInt("PlayerID"),
                        rs.getString("GameMode"),
                        rs.getString("Word"),
                        rs.getInt("RemainingAttempts"),
                        rs.getString("Status")
                );
                gamesList.add(game);
            }
        }
        return gamesList;
    }
}


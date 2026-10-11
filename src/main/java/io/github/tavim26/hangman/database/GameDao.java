package io.github.tavim26.hangman.database;

import io.github.tavim26.hangman.model.GameRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDao
{
    public void addGame(GameRecord game) throws SQLException
    {
        String query = "INSERT INTO game (PlayerID, Difficulty, Category, Word, RemainingAttempts, Status)"
                + " VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, game.getPlayerID());
            stmt.setString(2, game.getDifficulty());
            stmt.setString(3, game.getCategory());
            stmt.setString(4, game.getWord());
            stmt.setInt(5, game.getRemainingAttempts());
            stmt.setString(6, game.getStatus());
            stmt.executeUpdate();
        }
    }

    /** Returns the player's games, most recent first. */
    public List<GameRecord> findByPlayer(int playerID) throws SQLException
    {
        String query = "SELECT * FROM game WHERE PlayerID = ? ORDER BY GameID DESC";
        List<GameRecord> games = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, playerID);

            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next())
                {
                    games.add(new GameRecord(
                            rs.getInt("GameID"),
                            rs.getInt("PlayerID"),
                            rs.getString("Difficulty"),
                            rs.getString("Category"),
                            rs.getString("Word"),
                            rs.getInt("RemainingAttempts"),
                            rs.getString("Status")
                    ));
                }
            }
        }
        return games;
    }
}
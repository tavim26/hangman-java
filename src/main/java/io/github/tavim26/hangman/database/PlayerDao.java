package io.github.tavim26.hangman.database;

import io.github.tavim26.hangman.model.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDao
{
    public void addPlayer(Player player) throws SQLException
    {
        String query = "INSERT INTO player (Username, Email, PasswordHash) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setString(1, player.getUsername());
            stmt.setString(2, player.getEmail());
            stmt.setString(3, player.getPasswordHash());
            stmt.executeUpdate();
        }
    }

    /** Returns the player with the given username, or null if there is none. */
    public Player findByUsername(String username) throws SQLException
    {
        String query = "SELECT * FROM player WHERE Username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery())
            {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    public boolean existsByUsername(String username) throws SQLException
    {
        return exists("SELECT 1 FROM player WHERE Username = ?", username);
    }

    public boolean existsByEmail(String email) throws SQLException
    {
        return exists("SELECT 1 FROM player WHERE Email = ?", email);
    }

    private boolean exists(String query, String value) throws SQLException
    {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setString(1, value);

            try (ResultSet rs = stmt.executeQuery())
            {
                return rs.next();
            }
        }
    }

    private Player mapRow(ResultSet rs) throws SQLException
    {
        return new Player(
                rs.getInt("PlayerID"),
                rs.getString("Username"),
                rs.getString("Email"),
                rs.getString("PasswordHash")
        );
    }
}